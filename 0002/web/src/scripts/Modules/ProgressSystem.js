// ali - analise log interface
if(typeof(ali) == 'undefined') ali = function(){};
ali.ProgressSystem = function(stage)
{
	this._stage = stage
	var overLayerStyle =
	{
		position    : "absolute",
		height      : "100%",
		width       : "100%",
		background  : "rgba(0, 0, 0, 0.5)",
		'z-index'   : "101"
	};

	var alertContainerStyle =
	{
		width : "45%",
		margin: "auto",
		'margin-top': "20%"
	}
	this.overLayer = $("<div/>").css(overLayerStyle).addClass("progress-system-overlay");
	this.alertContainer = $("<div/>").css(alertContainerStyle).addClass("progress-alert-container");
	this.alert = $("<div/>").addClass("alert alert-info");
	this.alertText = $("<div/>");


	this.progress = $("<div/>").addClass("progress progress-striped active");
	this.bar = $("<div/>").addClass("progress-bar progress-bar-success");
	this.bar.attr('role','progressbar');
	this.bar.attr('aria-valuemin','0');
	this.bar.attr('aria-valuemax','100');

	this.progress.append(this.bar);
	this.overLayer.append(this.alertContainer);
	this.alertContainer.append(this.alert);
	this.alert.append(this.alertText);
	this.alert.append(this.progress);

	this.startProgress = function()
	{
		this.bar.text("0% complete");
		this.bar.attr('aria-valuenow','0');
		this.bar.css({"width": "0%"});
		this._stage.overlay.append(this.overLayer);
	}

	this.progressText = function(value)
	{
		this.alertText.text(value);
	}

	this.stopProgress = function()
	{
		this.overLayer.remove();
	}

	this.setProgress = function(value)
	{
		this.bar.text(value + "% complete");
		this.bar.attr('aria-valuenow', parseFloat(value));
		this.bar.css({"width": parseFloat(value) + "%"});
	}

}
