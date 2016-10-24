// ali - analise log interface
if(typeof(ali) == 'undefined') ali = function(){};
ali.DirController = function(container, mainController)
{
	this._container = container
	this._mainController = mainController;
	this.selectDir = function(selectedDir)
	{
		this._mainController.selectDir(selectedDir);
		this._directory.selectDir(selectedDir);
		this._mainController.threadsViewer.loadThreads(selectedDir.fullPath);
	}
	this._load = function()
	{
		var self = this;
		var data = ali.CONST.GET_DIR_TREE;
		sendRequest("execute", data, function(response)
		{
			function getDirectoriesFromJson(json, path)
			{
				/*	console.log(json);
				 console.log(json.name);
				 console.log(json["name"]);*/
				var dir = new ali.Directory(json["name"], self);
				dir.path = path;
				dir.isOpen = json["isOpen"];
				if (json["directories"]!=undefined && json["directories"].length > 0)
				{
					var i,l;
					l = json["directories"].length;
					for(i = 0; i < l; i++)
					{
						dir.addDir(getDirectoriesFromJson(json["directories"][i], dir.fullPath));
					}
				}
				if (json["files"]!=undefined && json["files"].length > 0)
				{
					var i,l;
					l = json["files"].length;
					for(i = 0; i < l; i++)
					{
						dir.addFile(new ali.File(json["files"][i]));
					}
				}

				return dir;
			}
			self._directory = getDirectoriesFromJson(JSON.parse(response), "");
			$(self._container).on("DOMSubtreeModified",
				function()
				{
					self._directory.closeFast();
					$(this).off("DOMSubtreeModified");
				}
			);
			self._container.append(self._directory.dir);
		});
	}

	this._load();
}
