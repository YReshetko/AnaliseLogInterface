// ali - analise log interface
if(typeof(ali) == 'undefined') ali = function(){};
ali.PanelSystem = function(stage)
{
	this._stage = stage;
	this._draggableRect = this._stage.panelsRect;
	this._panels = new Array();
	/**
	 * @param json
	 * {
	 *  x : x position on the page,
	 *  y : y position on the page,
	 *  width : start width of panel,
	 *  height : start height of panel,
	 *  title : title of the panel
	 *  resizeble : if it should be resizeble - true
	 *  draggable : if it should be draggable - true
	 * }
	 */
	this.getPanel = function(json)
	{
		//  TODO Create factory of panels and save settings on server for users
		json.x = this._draggableRect.x + json.x;
		json.y = this._draggableRect.y + json.y;
		json.width = json.width - this._draggableRect.x;
		json.height = json.height - this._draggableRect.y;

		var panel = new ali.Panel(json);
		this._stage.panels.append(panel.panel);
		panel.resizeble = (json.resizeble == 'undefined')?false:json.resizeble;
		panel.draggable = (json.draggable == 'undefined')?false:json.draggable;
		panel.dragRectangle = this._draggableRect;
		this._panels.push(panel);
		return panel;
	}
}
