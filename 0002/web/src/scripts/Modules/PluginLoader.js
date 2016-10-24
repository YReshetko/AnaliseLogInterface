function initPluginLoader(container)
{
	var target = container[0];
	target.addEventListener("dragover", function(event)
	{
		event.preventDefault();
		$(target).css("background-color","rgba(150, 0, 0, 0.2)");
	}, false);
	target.ondragleave = function(event)
	{
		event.preventDefault();
		$(target).css("background-color","");
	}
	target.addEventListener("drop", function(event)
	{
		event.preventDefault();
		$(target).css("background-color","");
		var i = 0;
		var files = event.dataTransfer.files;
		var len = files.length;
		var pluginForms = new Array();
		modalWindow.setTitle("TestWindow");
		modalWindow.addButton("Upload", function(){
			console.log("Push button");
			console.log(JSON.stringify(pluginForms[0].pluginDescription));
			for (i=0; i < len; i++)
			{
				var formData = new FormData();
				var pluginConfig = JSON.stringify(pluginForms[0].pluginDescription);
				formData.append("file", files[i]);
				formData.append("config", pluginConfig);
				uploadFile("pluginupload", formData, function(response){
					console.log(response);
					mainController.pluginsController.load();
				});
			}
			modalWindow.hide();
		});

		for(; i < len; i++)
		{
			pluginForms.push(new ali.PluginForm(files[i].name))
			modalWindow.setContent(pluginForms[i].element);
		}

		modalWindow.show();
	}, false);
}
if(typeof(ali) == 'undefined') ali = function(){};
ali.PluginForm = function(name)
{
	this._name = name;
	this._imputs = new Object();

	this._container = $("<div/>").addClass("plugin-form");
	this._jarName = $("<h5/>").addClass("modal-title");
	this._jarName.append(this._name);
	this._container.append(this._jarName);

	this._container.append(getRow2X2(getInput("package name", "glyphicon-folder-open", this._imputs, "packageName"),
		getInput("class name", "glyphicon-file", this._imputs, "className")));

	this._container.append(getRow2X2(getInput("plugin label", "glyphicon-link", this._imputs, "label"),
		getInput("plugin type", "glyphicon-registration-mark", this._imputs, "pluginType")));

	this._container.append(getRow2X2(getTextArea("short description", "glyphicon-comment", this._imputs, "shortDescription"),
		getTextArea("full description", "glyphicon-comment", this._imputs, "fullDescription")));

	Object.defineProperties(this,{
		element : {
			get : function(){
				return this._container;
			}
		},
		pluginDescription : {
			get : function(){
				var out =
				{
					packageName : this._imputs["packageName"][0].value,
					className : this._imputs["className"][0].value,
					label : this._imputs["label"][0].value,
					pluginType : this._imputs["pluginType"][0].value,
					shortDescription : this._imputs["shortDescription"][0].value,
					fullDescription : this._imputs["fullDescription"][0].value
				};
				return out;
			}
		}
	});
}
function getGroupControl(glyphiconType, input)
{
	var inputGroup = $("<div/>").addClass("input-group");
	var labelZone = $("<span/>").addClass("input-group-addon");
	var label = $("<span/>").addClass("glyphicon " + glyphiconType);
	labelZone.append(label);

	inputGroup.append(labelZone);
	inputGroup.append(input);
	return inputGroup;
}

function getInput(placeHolder, glyphiconType, inputs, prop)
{
	var input = $("<input/>").addClass("form-control").attr("placeholder", placeHolder);
	inputs[prop] = input;
	return getGroupControl(glyphiconType, input);
}
function getTextArea(placeHolder, glyphiconType, inputs, prop)
{
	var input = $("<textarea/>").addClass("form-control");
	input.attr("placeholder", placeHolder);
	input.attr("type", "text");
	input.css("resize", "none");
	inputs[prop] = input;
	return getGroupControl(glyphiconType, input);
}
function getRow2X2(first, second)
{
	var row = $("<div/>").addClass("row");
	var col1 = $("<div/>").addClass("col-md-6");
	var col2 = $("<div/>").addClass("col-md-6");
	col1.append(first);
	col2.append(second);
	row.append(col1);
	row.append(col2);
	return row;
}
