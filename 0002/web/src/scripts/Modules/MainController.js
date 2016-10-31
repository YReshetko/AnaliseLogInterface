// ali - analise log interface
if(typeof(ali) == 'undefined') ali = function(){};
ali.MainController = function(panelSystem)
{
	this._panelSystem = panelSystem;
	this._dirPanel = this._panelSystem.getPanel(ali.CONST.DIR_PANEL_DEFAULT_CONFIG);
	this._filePanel = this._panelSystem.getPanel(ali.CONST.FILE_PANEL_DEFAULT_CONFIG);
	this._threadPanel = this._panelSystem.getPanel(ali.CONST.THREADS_PANEL_DEFAULT_CONFIG);
	this._pluginsPanel = this._panelSystem.getPanel(ali.CONST.PLUGINS_PANEL_DEFAULT_CONFIG);
	initPluginLoader(this._pluginsPanel.container);
	this._lastSelectedDir = null;
	this.selectDir = function(dir)
	{
		this._lastSelectedDir = dir;
		this._fileController.setSelectedDir(dir);
	}

	this.updateSelectedDir = function()
	{
		if(this._lastSelectedDir)
		{
			this._dirController.selectDir(this._lastSelectedDir);
		}
	}



	this._threadsViewer = new ali.ThreadsViewer(this._threadPanel.container);
	this._dirController = new ali.DirController(this._dirPanel.container, this);
	this._fileController = new ali.FileController(this._filePanel.container);
    this._pluginsController = new ali.PluginsController(this._pluginsPanel.container);
	Object.defineProperties(this,{
		threadsViewer : {
			get : function()
			{
				return this._threadsViewer;
			}
		},
		pluginsController : {
		    get : function()
		    {
		        return this._pluginsController;
		    }
		},
		fileController : {
			get : function()
			{
				return this._fileController;
			}
		}
	});

	this.executePlugins = function()
	{
		var threadSet = this._threadsViewer.getThreadsSet();
		var plugins = this._pluginsController.getSelectedPlugins();
		if(plugins.length > 0 && threadSet.names.length > 0)
		{
			var data =
			{
				plugins : plugins,
				threadsSet : threadSet
			}
			var command = ali.CONST.EXECUTE_PLUGINS;
			command["data"] = JSON.stringify(data);

			sendRequest("execute", command, function(response){}, null);
		}
		//TODO Implement retrieving log threads and plugins and send it to execution
		//ali.CONST.EXECUTE_PLUGINS
	}
}
