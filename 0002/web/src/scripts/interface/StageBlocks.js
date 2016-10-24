// ali - analise log interface
if(typeof(ali) == 'undefined') ali = function(){};
ali.Stage = function(mainContainer)
{
	this._mainContainer = mainContainer;
	this._menuHeight = 21;
	this._toolsWidth = 22;
	this._rect = {
		x : this._toolsWidth,
		y : this._menuHeight,
		height : document.documentElement.clientHeight - this._menuHeight,
		width : document.documentElement.clientWidth - this._toolsWidth
	}
	var overlayStyle =
	{
		position    : "absolute",
		height      : "100%",
		width       : "100%"
	}
	var mainContainerStyle =
	{
		height : "100%",
		width : "100%"
	}
	var menuContainerStyle =
	{
		position    : "absolute",
	    width :  "100%",
		'max-height' : this._menuHeight + "px",
		'min-height' : this._menuHeight + "px",
		//border : "solid 1px"
		'background-color' : 'rgba(160, 160, 160, 0.3)'
	}
	var toolsContainerStyle =
	{
		position    : "absolute",
		top : this._menuHeight + "px",
		height : this._rect.height,
		'max-width' : this._toolsWidth + "px",
		'min-width' : this._toolsWidth + "px",
		//border : "solid 1px",
		display : "inline-block",
		'white-space' : 'nowrap',
		'background-color' : 'rgba(160, 160, 160, 0.3)'
	}
	var panelsContainerStyle =
	{
		display : "inline-block",
		'white-space' : 'nowrap'
	}
	this._mainContainer.css(mainContainerStyle);
	this._menuContainer = $("<div/>").css(menuContainerStyle).addClass("ali-menu-container");
	this._toolsContainer = $("<div/>").css(toolsContainerStyle).addClass("ali-tools-container");
	this._panelsContainer = $("<div/>").css(panelsContainerStyle).addClass("ali-panels-container");
	this._overlayContainer = $("<div/>").css(overlayStyle).addClass("ali-overlay-container");

	this._mainContainer.append(this._overlayContainer);
	this._mainContainer.append(this._menuContainer);
	this._mainContainer.append(this._toolsContainer);
	this._mainContainer.append(this._panelsContainer);

	Object.defineProperties(this,{
		menu : {
			get : function()
			{
				return this._menuContainer;
			}
		},
		tools : {
			get : function()
			{
				return this._toolsContainer;
			}
		},
		panels : {
			get : function()
			{
				return this._panelsContainer;
			}
		},
		overlay : {
			get : function()
			{
				return this._overlayContainer;
			}
		},
		panelsRect : {
			get : function()
			{
				return this._rect;
			}
		}
	});
}