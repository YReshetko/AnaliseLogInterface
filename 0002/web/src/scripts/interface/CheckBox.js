// ali - analise log interface
if(typeof(ali) == 'undefined') ali = function(){};
ali.CheckBox = function(size)
{
	this._isSelect = false;
    var boxStyle =
    {
	   	'font-size' : size + "pt"
    }
	this._checkBox = $("<div/>").css(boxStyle).addClass("glyphicon glyphicon-unchecked");
	Object.defineProperties(this,{
		box : {
			get : function(value)
			{
				return this._checkBox;
			}
		},
		select : {
			get : function()
			{
				return this._isSelect;
			},
			set : function(value)
			{
				this._isSelect = value;
				if(value)
				{
					this._checkBox.removeClass("glyphicon-unchecked");
					this._checkBox.addClass("glyphicon-check");
				}
				else
				{
					this._checkBox.removeClass("glyphicon-check");
					this._checkBox.addClass("glyphicon-unchecked");
				}
			}
		}
	});

	var self = this;
	this._checkBox.on("mousedown", function(event){
		self.select = !self.select;
	})
}