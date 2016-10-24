
function sendRequest(url, data, callback, config)
{
	$.ajax(
		{
			type    : "POST",
			url     : url,
			data    : data,
			context : document.body
		}
	).done(
		function(response)
		{
			try{
				var respObj = JSON.parse(response);
				if(respObj.command && respObj.command == "CONNECT_TO_PROCESS")
				{
					checkProcesses(config);
				}
			}
			catch (e)
			{
				console.error(e);
			}
			callback(response);
		}
	);
}

function download(fileNameOnServer, fileName)
{
	$.ajax(
		{
			type    : "POST",
			url     : "download",
			data    : {data : fileNameOnServer},
			context : document.body
		}
	).done(function(response){
			var a = document.createElement("a");
			var file = new Blob([response], {type: "text/plain"});
			var logName = fileName + ".log";
			if (window.navigator.msSaveOrOpenBlob)
			{
				window.navigator.msSaveOrOpenBlob(file, logName);
			}
			else
			{
				var url = URL.createObjectURL(file);
				a.href = url;
				a.download = logName;
				document.body.appendChild(a);
				a.click();
				setTimeout(function() {
					document.body.removeChild(a);
					window.URL.revokeObjectURL(url);
				}, 0);
			}
		})
}
function uploadFile(url, data, callback)
{
	var xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.open("POST", url, true);
	xmlHttpRequest.onreadystatechange = function(response)
	{
		callback(response);
	}
	xmlHttpRequest.send(data);
}

function checkProcesses(config)
{
	var eventSource;
	eventSource = new EventSource("progress");
	eventSource.addEventListener('open', function(event)
	{
		progressSystem.startProgress();
	}, false);

	eventSource.addEventListener('CURRENT_PROGRESS', function(event)
	{
		progressSystem.setProgress(event.data);
	}, false);
	eventSource.addEventListener('SET_LABEL_PROGRESS', function(event)
	{
		progressSystem.progressText(ali.CONST.PROGRESS_LABELS[event.data]);
	}, false);

	eventSource.addEventListener('GET_PROCESS_FILES', function(event)
	{
		progressSystem.setProgress("100");
		mainController.updateSelectedDir();
	}, false);

	eventSource.addEventListener('DOWNLOAD_THREAD', function(event)
	{
		progressSystem.setProgress("100");
		if(config && config.fileName)
		{
			download(event.data, config.fileName);
		}
		else
		{
			download(event.data, "unknown");
		}
	}, false);
	eventSource.addEventListener('COMPLETE_ALL_PROCESSES', function(event) {
		progressSystem.stopProgress();
		eventSource.close();
	}, false);
}

