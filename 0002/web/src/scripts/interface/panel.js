// ali - analise log interface
if(typeof(ali) == 'undefined') ali = function(){};
/*
 * config{
 *     x      - panel left position
 *     y      - panel top position
 *     width  - panel width
 *     height - panel height
 *     title  - panel title
 * }
 */
ali.BasePanel = function(){
	//this._title = config.title;
	this._panel;
	this._header;
	this._body;
	this._container;
	this._topButtonContainer;
	this._draggable = false;
	this._resizeble = false;
	this._headerHeight = 15;
	this._borderSize = 1;
	this._minWidth = 70;
	this._minHeight = 55;
	this._dragRectangle = null;

	this._init = function(config){
		var mainStyle = {
			position    : "absolute",
			cursor      : "auto"
		};
		var headerStyle = {
			height:this._headerHeight + "px",
			position: "relative",
			left:this._borderSize + "px",
			top:this._borderSize + "px",
			cursor: "pointer",
			overflow: "hidden"
		};
		var bodyStyle = {
			position: "relative",
			left:this._borderSize + "px",
			top: this._borderSize*2 + "px",
			cursor: "auto"
		};
		var containerStyle = {
			width:"100%",
			height:"100%",
			position: "relative",
			overflow: "auto"
		};
		var buttonContainerStylr = {
			width:"100%",
			position: "relative"
		}
		this._panel = $("<div/>").css(mainStyle).addClass("panel-main");
		this._header = $("<div/>").css(headerStyle).addClass("panel-header");
		this._body = $("<div/>").css(bodyStyle).addClass("panel-body");
		this._topButtonContainer = $("<div/>").css(buttonContainerStylr).addClass("panel-top-buttons");
		this._container = $("<div/>").css(containerStyle).addClass("panel-container");
		this._panel.append(this._header);
		this._panel.append(this._body);
		this._body.append(this._topButtonContainer);
		this._body.append(this._container);
		this.width = config.width;
		this.height = config.height;
		this.x = config.x;
		this.y = config.y;

	};
	Object.defineProperties(this,{
		header : {
			get : function(){
				return this._header;
			}
		},
		container : {
			get : function(){
				return this._container;
			}
		},
		buttonContainer : {
			get : function(){
				return this._topButtonContainer;
			}
		},
		panel : {
			get : function(){
				return this._panel;
			}
		},
		x : {
			get : function(){
				return this._x;
			},
			set : function(value){
				this._x = value;
			}
		},
		draggable : {
			get : function(){
				return this._draggable;
			},
			set : function(value){
				ali.move(this/*._header, this._panel*/, value);
				this._draggable = value;
			}
		},
		resizeble : {
			get : function(){
				return this._resizeble;
			},
			set : function(value){
				ali.resize(this._panel, this, value);
				this._resizeble = value;
			}
		},
		width : {
			get : function(){
				return parseInt($(this._panel).css("width"));
			},
			set : function(value){
				if(value<this._minWidth) value = this._minWidth;
				$(this._header).css("width", (value-this._borderSize*2)+"px");
				$(this._panel).css("width", value+"px");
				$(this._body).css("width", (value-this._borderSize*2)+"px");
			}
		},
		height : {
			get : function(){
				return parseInt($(this._panel).css("height"));
			},
			set : function(value){
				if(value<this._minHeight) value = this._minHeight;
				$(this._panel).css("height", value+"px");
				$(this._body).css("height", (value - this._headerHeight - (this._borderSize*3))+"px");
			}
		},
		x : {
			get : function(){
				return this._panel.offset().left;
			},
			set : function(value){
				var top = this._panel.offset().top;
				this._panel.offset({left: value, top: top});
			}
		},
		y : {
			get : function(){
				return this._panel.offset().top;
			},
			set : function(value){
				var left = this._panel.offset().left;
				this._panel.offset({left: left, top: value});
			}
		},
		borderSize : {
			get : function(){
				return this._borderSize;
			}
		},
		dragRectangle : {
			get : function(){
				return this._dragRectangle;
			},
			set : function(value){
				this._dragRectangle = value;
			}
		}
	});
}
ali.EvenDispatcher = function(){
	this._event = "";
	this.addEventListener = function(event, func){
		this._event = event;
	}
	this.removeEventListener = function(event, func){

	}
	this.dispatchEvent = function(event){

	}
}
ali.BasePanel.prototype = new ali.EvenDispatcher();

ali.Panel = function(config){
	this._title = config.title;
	this._init(config);
	this._titleObject = $("<div>"+this._title+"</div>").addClass("panel-title");
	this.header.append(this._titleObject);
	Object.defineProperties(this,{
		title : {
			get : function(){
				return this._title;
			},
			set : function(value){
				this._titleObject.empty();
				this._titleObject.append(value);
				this._title = value;
			}
		}
	});
}
ali.Panel.prototype = new ali.BasePanel();

ali.Block = function(config){
	this._headerHeight = 20;
	this._panels = new Array();
	var hearedHeight = this._headerHeight;

	_tab = function(title){
		this._tabHeight = 12;
		var tabStyle = {
			height:this._tabHeight + "px",
			top : (hearedHeight - this._tabHeight - 6) + "px",
			position: "relative",
			display: "inline",
			"white-space": "nowrap"
		};
		this._tab = $("<div>"+title+"\t"+"</div>").css(tabStyle).addClass("block-panel-tab");
	}
	this.addPanel = function(panel){
		panel.panel.remove();
		var tab = new _tab(panel.title);
		var id = this._panels.length;
		this._panels.push({
			panel : panel,
			tab : tab,
			content : panel.container.html()
		});
		this.header.append(tab._tab);
		var self = this;
		tab._tab.on("mousedown", function(e){
			self.select(id);
			self.draggable = false;
		});
		this.select(id);
	}
	this.select = function(id){
		var i,l;
		l = this._panels.length;
		for(i=0;i<l;i++){
			this._panels[i].tab._tab.removeClass("tab-select");
			this._panels[i].tab._tab.css("z-index", 1);
		}
		this.container.empty();
		this.container.append(this._panels[id].content);
		this._panels[id].tab._tab.addClass("tab-select");
		this._panels[id].tab._tab.css("z-index", 2);
	}
	this._init(config);
}
ali.Block.prototype = new ali.BasePanel();

ali.move = function(/*div, panel*/currentPanel, draggable){
	var div = currentPanel.header;
	var panel = currentPanel.panel;
	var self = currentPanel;
	function moveFunc(e){
		$(panel).css("z-index", 10);
		left = e.pageX - panel.offset().left;
		top1 = e.pageY - panel.offset().top;
		clear = function(){
			$(div).off("mouseup", clear);
			$(document).off("mouseup", clear);
			$(document).off("mousemove", movePanel);
			$(panel).css("z-index", 1);
		}
		movePanel = function(e){
			var newTop = e.pageY - top1;
			var newLeft = e.pageX - left;
			if(self.dragRectangle != null)
			{
				var rect = self.dragRectangle;
				var allowXPosition = rect.x + (rect.width - self.width);
				var allowYPosition = rect.y + (rect.height - self.height);
			    if (newTop > allowYPosition)
			    {
				    newTop = allowYPosition;
			    }
				if (newTop < rect.y)
				{
					newTop = rect.y;
				}
				if(newLeft > allowXPosition)
				{
					newLeft = allowXPosition;
				}
				if(newLeft < rect.x)
				{
					newLeft = rect.x;
				}
			}
			panel.offset({"top": newTop, "left":newLeft});
		}
		$(document).on("mousemove", movePanel);
		$(document).on("mouseup", clear);
		$(div).on("mouseup", clear);
	}
	if(draggable){
		$(div).on("mousedown", moveFunc);
	}else{
		try{
			$(div).off("mousedown", moveFunc);
		}catch(e){
			console.err("Can't remove draggable event listener from panel");
		}
	}
}

ali.resize = function(container, object, resizeble){
	var resizeTypes = {
		right       : {type : "right",      flag : false, cursor : "e-resize",      func : resizeRight      },
		left        : {type : "left",       flag : false, cursor : "e-resize",      func : resizeLeft       },
		up          : {type : "up",         flag : false, cursor : "n-resize",      func : resizeUp         },
		down        : {type : "down",       flag : false, cursor : "n-resize",      func : resizeDown       },
		rightUp     : {type : "rightUp",    flag : false, cursor : "nesw-resize",   func : resizeRightUp    },
		rightDown   : {type : "rightDown",  flag : false, cursor : "nwse-resize",   func : resizeRightDown  },
		leftUp      : {type : "leftUp",     flag : false, cursor : "nwse-resize",   func : resizeLeftUp     },
		leftDown    : {type : "leftDown",   flag : false, cursor : "nesw-resize",   func : resizeLeftDown   },
		none        : {type : "none",       flag : true,  cursor : "auto",          func : null             }
	};
	var resizeType = resizeTypes.none;
	function overPanel(e){
		$(container).on("mousemove", freeMouseMove);
		$(container).on("mousedown", resizeMouseDown);

	};
	function outPanel(e){
		$(container).off("mousemove", freeMouseMove);
		$(container).off("mousedown", resizeMouseDown);
	};
	function freeMouseMove(e){
		var x = e.offsetX;
		var y = e.offsetY;
		var xLeft = x<=object.borderSize;
		var xRight = x>=(object.width - object.borderSize);
		var yUp = y<=object.borderSize;
		var yDown = y>=(object.height - object.borderSize);

		resizeTypes.right.flag = xRight && !yUp && !yDown;
		resizeTypes.left.flag = xLeft && !yUp && !yDown;
		resizeTypes.up.flag = yUp && !xRight && !xLeft;
		resizeTypes.down.flag = yDown && !xRight && !xLeft;

		resizeTypes.rightUp.flag = xRight && yUp;
		resizeTypes.rightDown.flag = xRight && yDown;
		resizeTypes.leftUp.flag = xLeft && yUp;
		resizeTypes.leftDown.flag = xLeft && yDown;
		resizeTypes.none.flag = true;
		resizeType = resizeTypes.none;

		for(obj in resizeTypes){
			if(resizeTypes[obj].flag){
				resizeType = resizeTypes[obj];
				$(container).css("cursor", resizeType.cursor);
				return;
			}
		}
	}
	function resizeMouseDown(e){
		if(resizeType.type == "none") return;
		$(container).off("mousemove", freeMouseMove);
		$(document).on("mousemove", resizeType.func);
		$(document).on("mouseup", removeResize);
	}
	function resizeRight(e){
		var newWidth = e.pageX - object.x;
		if(object.dragRectangle != null)
		{
			var allowWidth = object.dragRectangle.x + object.dragRectangle.width - object.x;
			if(newWidth > allowWidth)
			{
				newWidth = allowWidth;
			}
		}
		object.width = newWidth;
	}
	function resizeLeft(e){
		var oldX = object.x;
		var rightX = oldX + object.width;
		object.width = object.width + (oldX - e.pageX);
		object.x = rightX - object.width;
	}
	function resizeDown(e){
		var newHeight = e.pageY - object.y;
		if(object.dragRectangle != null)
		{
			var allowHeight = object.dragRectangle.y + object.dragRectangle.height - object.y;
			if(newHeight > allowHeight)
			{
				newHeight = allowHeight;
			}
		}
		object.height = newHeight;
	}
	function resizeUp(e){
		var oldY = object.y;
		var downY = oldY + object.height;
		var oldHeight = object.height;

		if(object.dragRectangle != null)
		{
			object.height = object.height + (oldY - e.pageY);
			object.y = downY - object.height;
			if (object.y < object.dragRectangle.y)
			{
				object.height = oldHeight;
				object.y = oldY;
			}
		}
		else{
			object.height = object.height + (oldY - e.pageY);
			object.y = downY - object.height;
		}

	}
	function resizeRightUp(e){
		resizeRight(e);
		resizeUp(e);
	}
	function resizeRightDown(e){
		resizeRight(e);
		resizeDown(e);
	}
	function resizeLeftUp(e){
		resizeLeft(e);
		resizeUp(e);
	}
	function resizeLeftDown(e){
		resizeLeft(e);
		resizeDown(e);
	}
	function removeResize(e){
		$(document).off("mousemove", resizeType.func);
		$(document).off("mouseup", removeResize);
		$(container).on("mousemove", freeMouseMove);
	}
	if(resizeble){
		$(container).on("mouseover", overPanel);
		$(container).on("mouseout", outPanel);
	}else{
		$(container).off("mouseover", overPanel);
		$(container).off("mouseout", outPanel);
	}
}