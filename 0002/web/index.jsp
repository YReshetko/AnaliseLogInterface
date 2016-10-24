<%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <!--Libraries-->
    <script src="src/lib/jquery-2.1.3.min.js"></script>
    <script src="src/lib/angular.min.js"></script>
    <script src="src/lib/bootstrap.min.js"></script>
    <!--Custom scripts-->
    <script src="src/scripts/Modules/PluginLoader.js"></script>
    <script src="src/scripts/interface/StageBlocks.js"></script>
    <script src="src/scripts/interface/CheckBox.js"></script>
    <script src="src/scripts/interface/panel.js"></script>
    <script src="src/scripts/interface/PanelSystem.js"></script>
    <script src="src/scripts/interface/const.js"></script>
    <script src="src/scripts/interface/directories.js"></script>
    <script src="src/scripts/interface/files.js"></script>
    <script src="src/scripts/interface/DropDown.js"></script>
    <script src="src/scripts/Modules/FileController.js"></script>
    <script src="src/scripts/Modules/DirController.js"></script>
    <script src="src/scripts/Modules/MainController.js"></script>
    <script src="src/scripts/Modules/ProgressSystem.js"></script>
    <script src="src/scripts/Modules/ThreadsViewer.js"></script>
    <script src="src/scripts/Modules/ModalWindow.js"></script>
    <script src="src/scripts/Modules/PluginsController.js"></script>
    <script src="src/scripts/Modules/ToolsPanel.js"></script>
    <script src="src/init.js"></script>
    <script src="src/scripts/net/requestSender.js"></script>
    <!--Styles-->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/bootstrap-theme.min.css" rel="stylesheet">
    <link href="css/stylesheet.css" rel="stylesheet">
</head>
<body>
<div class="stage">

</div>
<script type="text/javascript">
    if(window.EventSource !== undefined){
        console.log("Event stream is supported");
    } else {
        console.log("Event stream is NOT supported");
    }
</script>
<script>
    window.onload = init();
</script>
</body>
</html>