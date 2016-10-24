// ali - analise log interface
if(typeof(ali) == 'undefined') ali = function(){};
ali.ToolsSystem = function(stage, mainController)
{
  	this._stage = stage;
	this._mainController = mainController;
	this._toolsPanel = stage.tools;

	var threadBlockStyle =
	{
		'padding-top' : "10px"
	}
	this._logBlock = $("<div/>").addClass("ali-tools-log-block");
	this._threadBlock = $("<div/>").css(threadBlockStyle).addClass("ali-thread-log-block");

	this._toolsPanel.append(this._logBlock);
	this._toolsPanel.append(this._threadBlock);

	this._logButtons = new Array();

	this._logButtons.push(new ali.ToolButton("glyphicon-play", "Parse selected log files", "PARSE_LOG", this));

	this._threadButtons = new Array();

	this._threadButtons.push(new ali.ToolButton("glyphicon-refresh", "Change thread view", "CHANGE_THREAD_VIEW", this));
	this._threadButtons.push(new ali.ToolButton("glyphicon-check", "Mark all threads", "MARK_ALL_THREADS", this));
	this._threadButtons.push(new ali.ToolButton("glyphicon-unchecked", "Unmark all threads", "UNMARK_ALL_THREADS", this));
	this._threadButtons.push(new ali.ToolButton("glyphicon-download-alt", "Download selected threads", "DOWNLOAD_SELECTED_THREADS", this));
	this._threadButtons.push(new ali.ToolButton("glyphicon-trash", "Remove selected threads", "REMOVE_THREADS", this));
	var i,l;
	l = this._logButtons.length;

	for(i=0;i<l;i++)
	{
		this._logBlock.append(this._logButtons[i].button);
	}
	l = this._threadButtons.length;
	for(i=0;i<l;i++)
	{
		this._threadBlock.append(this._threadButtons[i].button);
	}

	this.execute = function(event)
	{
	   if (event == "PARSE_LOG")
	   {
		   this._mainController.fileController.sendFilesToProcess();
	   }
		else if (event == "CHANGE_THREAD_VIEW")
	   {
		   this._mainController.threadsViewer.changeThreadView();
	   }
		else if (event == "MARK_ALL_THREADS")
	   {
		   this._mainController.threadsViewer.selectAll(true);
	   }
		else if (event == "UNMARK_ALL_THREADS")
	   {
		   this._mainController.threadsViewer.selectAll(false);
	   }
	   else if (event == "REMOVE_THREADS")
	   {
		   this._mainController.threadsViewer.removeSelectedThreads();
	   }
	   else if (event == "DOWNLOAD_SELECTED_THREADS")
	   {
		   this._mainController.threadsViewer.downloadSelected();
	   }
	}
}

ali.ToolButton = function(buttonType, hint, callBackEvent, toolsSystem)
{
    this._buttonType = buttonType;
	this._hint = hint;
	this._callBackEvent = callBackEvent;

	var labelStyle =
	{
		'font-size' : "12pt"
	}
	var buttonStyle =
	{
		width : "100%",
		padding : "2px"
	}
	this._button = $("<div/>").css(buttonStyle).addClass("ali-tool-button");
	this._buttonLabel = $("<span/>").css(labelStyle).addClass("glyphicon " + this._buttonType);
	this._button.attr('title', this._hint);
	this._button.append(this._buttonLabel);

	var self = this;
	this._button.on("mousedown", function()
	{
		toolsSystem.execute(self._callBackEvent);
	});

	Object.defineProperties(this,{
		button : {
			get : function()
			{
				return this._button;
			}
		}
	});

}