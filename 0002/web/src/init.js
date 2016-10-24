/**
 * Created with IntelliJ IDEA.
 * User: Yurchik
 * Date: 12.01.16
 * Time: 19:45
 * To change this template use File | Settings | File Templates.
 */

var progressSystem;
var modalWindow;
var mainController;
var toolsController;
function init()
{
	checkProcesses(null);
	var startX = 0;
	var startY = 0;

	var stage = new ali.Stage($(".stage"));
    var panelSystem = new ali.PanelSystem(stage);
	mainController = new ali.MainController(panelSystem);
	toolsController = new ali.ToolsSystem(stage, mainController);
	progressSystem = new ali.ProgressSystem(stage);
	modalWindow = new ali.ModalWindow();
}



/**
 * Legacy
 */
/*function init(){
	var p1 = new td.Panel({x : 10, y : 10, width : 300, height : 600, title : "PANEL 1"});
	var p2 = new td.Panel({x : 10, y : 10, width : 200, height : 500, title : "�����"});

	var p3 = new td.Panel({x : 10, y : 10, width : 100, height : 400, title : "Block 2 p1"});
	var p4 = new td.Panel({x : 10, y : 10, width : 100, height : 400, title : "Block 2 p2"});

	$(".stage").append(p1.panel);  */
	/*$(".stage").append(p2.panel);
	$(".stage").append(p3.panel);  */
	//$(".stage").append(p3.panel);

	/*p1.draggable = true;
	p2.draggable = true;
	p3.draggable = true;
	//p3.draggable = true;

	p1.resizeble = true;
	p2.resizeble = true;
	p3.resizeble = true; */

	/*p1.title = "CHANGE NAME";

	p1.container.append("CONTENT P1 \r\nCONTENT P1 \r\nCONTENT P1 \r\nCONTENT P1 \r\nCONTENT P1 \r\nCONTENT P1 \r\nCONTENT P1 \r\nCONTENT P1 \r\nCONTENT P1 \r\nCONTENT P1 \r\n");
	p2.container.append("CONTENT P2 \r\nCONTENT P2 \r\nCONTENT P2 \r\nCONTENT P2 \r\nCONTENT P2 \r\nCONTENT P2 \r\nCONTENT P2 \r\nCONTENT P2 \r\nCONTENT P2 \r\nCONTENT P2 \r\n");


	p3.container.append("CONTENT Block 2 p1 \r\nCONTENT Block 2 p1 \r\nCONTENT Block 2 p1 \r\nCONTENT Block 2 p1 \r\nCONTENT Block 2 p1 \r\nCONTENT Block 2 p1 \r\nCONTENT Block 2 p1 \r\n");
	p4.container.append("CONTENT Block 2 p2 \r\nCONTENT Block 2 p2 \r\nCONTENT Block 2 p2 \r\nCONTENT Block 2 p2 \r\nCONTENT Block 2 p2 \r\nCONTENT Block 2 p2 \r\nCONTENT Block 2 p2 \r\n");


	var b1 = new td.Block({x : 10, y : 10, width : 250, height : 400});
	var b2 = new td.Block({x : 350, y : 10, width : 250, height : 400});
	$(".stage").append(b1.panel);
	$(".stage").append(b2.panel);

	b1.addPanel(p1);
	b1.addPanel(p2);
	b1.resizeble = true;
	b1.draggable = true;


	b2.addPanel(p3);
	b2.addPanel(p4);
	b2.addPanel(p1);
	b2.addPanel(p2);
	b2.resizeble = true;
	b2.draggable = true;*/
	//p2.container.append("SomeString<br>SomeString<br>SomeString<br>SomeString<br>SomeString<br>SomeString<br>SomeString<br>SomeString<br>SomeString<br>SomeString<br>SomeString<br>SomeString<br>");
//}


