// ali - analise log interface
if(typeof(ali) == 'undefined') ali = function(){};
ali.Directory = function(name, callback)
{
	this._dirs = new Array();
	this._files = new Array();
	this._name = name;
	this._callback = callback;
	this._path = "";
	this._isOpen = false;

	var mainContainerStyle =
	{
		padding     : 0,
		position    : "relative",
		cursor      : "auto"
	}
	var labelContainerStyle =
	{
		padding     : 0,
		'white-space' : "nowrap"
	}
	var imageCloseStyle =
	{
		display     : "inline-block",
		//content     : "url(img/folderLabels/closeFolder.png)",
		'font-size' : "14px",
		color       : "blue",
		padding     : 0
	}
	var imageOpenStyle =
	{
		display     : "inline-block",
		//content     : "url(img/folderLabels/openFolder.png)",
		padding     : 0
	}
	var nameContainer =
	{
		display             : "inline-block",
		cursor              : "default",
		'vertical-align'    : "3px",
		padding             : 0

	}
	var childrenContainerStyle =
	{
		padding         : 0,
		position        : "relative",
		cursor          : "auto"
	}

	this._mainContainer = $("<div/>").css(mainContainerStyle).addClass("dir-main-container");
	this._labelContainer = $("<div/>").css(labelContainerStyle).addClass("dir-label-container");
	this._imageContainer = $("<span/>").css(imageCloseStyle).addClass("dir-img-container glyphicon glyphicon-folder-close");
	this._nameContainer = $("<span/>").css(nameContainer).addClass("dir-name-container");
	this._childrenContainer = $("<div/>").css(childrenContainerStyle).addClass("dir-child-container");

	this._mainContainer.append(this._labelContainer);
	this._labelContainer.append(this._imageContainer);
	this._labelContainer.append(this._nameContainer);
	this._mainContainer.append(this._childrenContainer);


	this._nameContainer.append(name);
	this._imageContainer.append()

	Object.defineProperties(this,{
		dir : {
			get : function()
			{
				return this._mainContainer;
			}
		},
		path : {
			get : function()
			{
				return this._path;
			},
			set : function(value)
			{
				this._path = value;
			}
		},
		fullPath : {
			get : function()
			{
				return this._path + "/" + this._name;
			}
		},
		isOpen : {
			get : function()
			{
				return this._isOpen;
			},
			set : function(value)
			{
				this._isOpen = value;
			}
		},
		files : {
			get : function()
			{
				return this._files;
			}
		}
	});

	this.addDir = function(value)
	{
		this._dirs.push(value);
		this._childrenContainer.append(value.dir);
	}
	this.addFile = function(value)
	{
		this._files.push(value);
	}
	this.open = function()
	{
		this._callback.selectDir(this);
		if (this._dirs.length > 0)
		{
			$(this._childrenContainer).show(100);
			//this._imageContainer.css(imageOpenStyle);
			this._imageContainer.removeClass("glyphicon-folder-close");
			this._imageContainer.addClass("glyphicon-folder-open");

			this.isOpen = true;
		}
	}
	this.close = function()
	{
		$(this._childrenContainer).hide(100);
		//this._imageContainer.css(imageCloseStyle);
		this._imageContainer.removeClass("glyphicon-folder-open");
		this._imageContainer.addClass("glyphicon-folder-close");
		this._callback.selectDir(this);
		this.isOpen = false;
	}
	this.selectDir = function(dir)
	{
		if(this == dir)
		{
			this._highLiteDir();
		}
		else
		{
			this._unHightLiteDir();
		}
		var i,l;
		l = this._dirs.length;
		for(i=0;i<l;i++)
		{
			this._dirs[i].selectDir(dir);
		}
	}
	this._highLiteDir = function()
	{
		this._nameContainer.addClass("dir-highlight")
	}
	this._unHightLiteDir = function()
	{
		this._nameContainer.removeClass("dir-highlight")
	}
	var self = this;
	$(this._labelContainer).on("click", function(event)
	{
		/*console.log(self.fullPath);*/
		if (self.isOpen)
		{
			self.close();
		}
		else
		{
			self.open();
		}
	});

	this.closeFast = function()
	{
		$(this._childrenContainer).hide();
		this._imageContainer.css(imageCloseStyle);
		this.isOpen = false;
		var i,l;
		l = this._dirs.length;
		for(i=0;i<l;i++)
		{
			this._dirs[i].closeFast();
		}
	}
}
