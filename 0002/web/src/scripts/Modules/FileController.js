// ali - analise log interface
if(typeof(ali) == 'undefined') ali = function(){};
ali.FileController = function(container)
{
	this._container = container;
	var emptyFolderStyle =
	{
		padding         : 0,
		margin          : "auto",
		'text-align'    : "center",
		'line-height'   : "50px"
	}
	this._emplyFolderLabel = $("<div/>").css(emptyFolderStyle).addClass("file-empty-container");
	this._emplyFolderLabel.append("-- Empty folder --");
	this.setSelectedDir = function(dir)
	{
		var files = dir.files;
		var i,l;
		this._currentFullPath = dir.fullPath;
		if(this._files && this._files.length > 0)
		{
			l = this._files.length;
			for (i=0;i<l;i++)
			{
				this._files[i].container.off("mousedown");
			}
			this._files = undefined;
		}
		$(this._container).empty();
		l = files.length;
		if(l>0)
		{
			var self = this;
			for (i=0;i<l;i++)
			{
				this._container.append(files[i].container);
				files[i].container.on("mousedown", function(){self.selectFile(this)});
			}
			this._files = files;
		}
		else
		{
			this._container.append(this._emplyFolderLabel);
		}
	}
	this.selectFile = function(fileContainer)
	{
		if (this._files && this._files.length > 0)
		{
			var i,l;
			l = this._files.length;
			for(i=0;i<l;i++)
			{
				var currentContainer = this._files[i].container[0];
				if(currentContainer == fileContainer)
				{
					this._files[i].selectFile();
				}
			}
		}

	}

	this.sendFilesToProcess = function()
	{
		if (this._files && this._files.length > 0)
		{
			var i,l;
			var selectedFiles = new Array();
			l = this._files.length;
			for (i = 0; i < l; i++)
			{
				if (this._files[i].isSelected)
				{
					selectedFiles.push(this._files[i].name)
				}
			}
			if (selectedFiles.length > 0)
			{
				var out = {
					"basePath" : this._currentFullPath,
					"selectedFiles" : selectedFiles
				}
				var request = ali.CONST.PROCESS_LOG_FILES;
				request["data"] = JSON.stringify(out);
				sendRequest("execute", request, function(response){
					console.log(response);
				})
				console.log(out);
			}
		}

	}
}
