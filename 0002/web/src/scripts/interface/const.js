// ali - analise log interface
if(typeof(ali) == 'undefined') ali = function(){};
ali.CONST = function(){};
/**
 * File constants
 */
ali.CONST.FILE_LABELS_BASE_PATH = "img/fileLabels/";
ali.CONST.UNKNOWN_FILE = ali.CONST.FILE_LABELS_BASE_PATH + "UNKNOWN.png";
ali.CONST.FILE_EXTENSION_IMG_MAPPING =
{
    xml : ali.CONST.FILE_LABELS_BASE_PATH + "XML.png",
    xsd : ali.CONST.FILE_LABELS_BASE_PATH + "XSD.png",
	log : ali.CONST.FILE_LABELS_BASE_PATH + "LOG.png"
};
ali.CONST.FILE_BUTTON_RUN = "img/buttons/run.png";
/**
 * MainController constants
 */
ali.CONST.DIR_PANEL_DEFAULT_CONFIG =
{
	x           : 10,
	y           : 10,
	width       : 300,
	height      : 300,
	title       : "Working directory",
	resizeble   : true,
	draggable   : true
}
ali.CONST.FILE_PANEL_DEFAULT_CONFIG =
{
	x           : 10,
	y           : 320,
	width       : 300,
	height      : 300,
	title       : "Files",
	resizeble   : true,
	draggable   : true
}
ali.CONST.THREADS_PANEL_DEFAULT_CONFIG =
{
	x           : 320,
	y           : 10,
	width       : 300,
	height      : 610,
	title       : "Threads",
	resizeble   : true,
	draggable   : true
}
ali.CONST.PLUGINS_PANEL_DEFAULT_CONFIG =
{
	x           : 630,
	y           : 10,
	width       : 300,
	height      : 610,
	title       : "Plugins",
	resizeble   : true,
	draggable   : true
}

/**
 * Commands to server
 */
ali.CONST.GET_DIR_TREE = {
	"command" : "GET_DIR_TREE"
}
ali.CONST.PROGRESS = {
	"command" : "PROGRESS"
}
ali.CONST.PROCESS_LOG_FILES = {
	"command" : "GET_PROCESS_FILES",
	"data"    : ""
}

ali.CONST.GET_PROCESSED_THREADS = {
	"command" : "GET_PROCESSED_THREADS",
	"data"    : ""
}
ali.CONST.GET_ALL_PLUGINS = {
    "command" : "GET_ALL_PLUGINS",
    "data"    : ""
}
ali.CONST.DOWNLOAD_THREAD = {
	"command" : "DOWNLOAD_THREAD",
	"data"    : ""
}
ali.CONST.REMOVE_THREADS = {
	"command" : "REMOVE_THREADS",
	"data"    : ""
}

ali.CONST.PROGRESS_LABELS = {
	"DOWNLOAD_THREAD" : "Preparing log file for downloading",
	"GET_PROCESS_FILES" : "Parsing log"
}

ali.CONST.THREADS_COLORS =
[
	17, 51, 102, 153, 204, 238
]