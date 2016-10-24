// ali - analise log interface
if(typeof(ali) == 'undefined') ali = function(){};
ali.ModalWindow = function()
{
	this._buttonClose = $("<div/>").addClass("close");
	this._buttonClose.attr('type','button');
	this._buttonClose.attr('data-dismiss','modal');
	this._buttonClose.attr('aria-hidden','true');
	this._buttonClose.append("&times;");

	this._label = $("<h4/>").addClass("modal-title");
	this._label.attr('id','myModalLabel');

	this._header = $("<div/>").addClass("modal-header");
	this._header.append(this._buttonClose);
	this._header.append(this._label);

	this._body = $("<div/>").addClass("modal-body");

	this._footer = $("<div/>").addClass("modal-footer");

	this._content = $("<div/>").addClass("modal-content");
	this._content.append(this._header);
	this._content.append(this._body);
	this._content.append(this._footer);

	this._dialog = $("<div/>").addClass("modal-dialog");
	this._dialog.append(this._content);

	this._modal = $("<div/>").addClass("modal fade");
	this._modal.attr('id','myModal');
	this._modal.attr('tabindex','-1');
	this._modal.attr('role','dialog');
	this._modal.attr('aria-labelledby','myModalLabel');
	this._modal.attr('aria-hidden','true');
	this._modal.append(this._dialog);

	this.setTitle = function(value)
	{
		this._label.append(value);
	}

	this.setContent = function(value)
	{
		this._body.append(value);
	}
	this.addButton = function(name, callback)
	{
		var button = $("<button/>").addClass("btn btn-default");
		button.attr('type','button');
		button.append(name);
		this._footer.append(button);
		button[0].addEventListener("mousedown", function(event){
			callback();
		});
	}

	this.large = function()
	{
		this._dialog.addClass("modal-lg");
	}
	this.small = function()
	{
		this._dialog.addClass("modal-sm");
	}

	this.clear = function()
	{
		this._dialog.removeClass("modal-lg");
		this._dialog.removeClass("modal-sm");
		this._label.empty();
		this._body.empty();
		this._footer.empty();
		this._modal[0].remove();
	}

	this.show = function()
	{
		$(".stage").append(this._modal);
		this._modal.modal('show');

	}
	this.hide = function()
	{
		this._modal.modal('hide');
	}

	var self = this;
	this._modal.on('hidden.bs.modal', function (e) {
		console.log("on hidden event");
		self.clear();
	})


}