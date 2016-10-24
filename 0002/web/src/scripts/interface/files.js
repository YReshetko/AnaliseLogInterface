// ali - analise log interface
if(typeof(ali) == 'undefined') ali = function(){};
ali.File = function(json)
{
	var reg = /\.[a-z]+\b/i;

    this._name = json["fileName"];
	this._isSelect = json["isSelect"];
	var match = reg.exec(this._name);
	var extension = "";
	if(match.length > 0){
		extension = match[match.length - 1];
		extension = extension.substr(1, extension.length-1);
	}
	this._extansion = extension;

	var mainContainerStyle =
	{
		padding         : 0,
		'white-space'   : "nowrap",
		'line-height'   : "28px",
		'max-height'   : "28px"

}
	var img = ali.CONST.FILE_EXTENSION_IMG_MAPPING[extension];
	if (!img)
	{
		img = ali.CONST.UNKNOWN_FILE;
	}
	var imageContainerStyle =
	{
		padding     : 0,
		margin      : "auto",
		content     : "url("+img+")",
		display     : "inline-block"
	}
	var nameContainerStyle =
	{
		padding         : 0,
		margin          : "auto",
		display         : "inline-block",
		'vertical-align': "top",
		cursor          : "default"


}
	this._mainContainer = $("<div/>").css(mainContainerStyle).addClass("file-main-container");
	this._imageContainer = $("<div/>").css(imageContainerStyle).addClass("file-image-container");
	this._nameContainer = $("<div/>").css(nameContainerStyle).addClass("file-name-container");

	this._mainContainer.append(this._imageContainer);
	this._mainContainer.append(this._nameContainer);
	this._nameContainer.append(this._name);
	Object.defineProperties(this,{
		container : {
			get : function()
			{
				return this._mainContainer;
			}
		},
		isSelected : {
			get : function()
			{
				return this._isSelect;
			}
		},
		name : {
			get : function()
			{
				return this._name;
			}
		}
	});

	this.selectFile = function()
	{
		if(this._isSelect)
		{
			this._unHightLiteFile();
		}
		else
		{
			this._highLiteFile();
		}
	}

	this._highLiteFile = function()
	{
		this._mainContainer.addClass("file-highlight");
		this._isSelect = true;
	}
	this._unHightLiteFile = function()
	{
		this._mainContainer.removeClass("file-highlight");
		this._isSelect = false;
	}
}
