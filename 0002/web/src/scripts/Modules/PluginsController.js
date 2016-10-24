// ali - analise log interface
if(typeof(ali) == 'undefined') ali = function(){};
ali.PluginsController = function(container)
{
    this._container = container;
    this._plugins = new Array();
    this.load = function()
    {
        var self = this;
        var data = ali.CONST.GET_ALL_PLUGINS;
        sendRequest("execute", data, function(response)
        {
            console.log(response);
            self.setAllPlugins(JSON.parse(response))
        });
    }
    this.setAllPlugins = function(pluginsDescriptor)
    {
        this.clear();
        var i,l;
        l = pluginsDescriptor["plugins"].length;
        for(i=0;i<l;i++)
        {
            this._plugins.push(new ali.PluginSample(pluginsDescriptor["plugins"][i]));
        }
        this.addToPanelAll();
    }
    this.clear = function()
    {
        while(this._plugins.length > 0)
        {
            this._plugins[0].clear();
            this._plugins.shift();
        }
    }

    this.addToPanelAll = function()
    {
        var i,l;
        l = this._plugins.length;
        for(i=0;i<l;i++)
        {
            this._container.append(this._plugins[i].plugin);
        }
    }
    this.load();
}

ali.PluginSample = function(pluginDescriptor)
{
    this._descriptor = pluginDescriptor;
    this._versions = this._descriptor["versions"];
    this._lastVersion = this._versions[this._versions.length - 1]["version"];
    var pluginBlockStyle =
    {
        padding         : "4px",
        margin          : "auto",
        'text-align'    : "left",
        border          : "solid 1px",
        'border-radius' : "3px",
        'background-color' : "rgba(16, 200, 16, 0.3)",
        cursor          : "default",
        "min-width"     : "250px"
    };

    var pluginLabelStyle =
    {
        display : "inline-block",
    }
    var pluginVersionStyle =
    {
        display : "inline-block",
        float   : "right"
    }
    this._plugin = $("<div/>").css(pluginBlockStyle).addClass("plugin-main-block");
    this._label = $("<div/>").css(pluginLabelStyle).addClass("plugin-label");
    this._version = $("<div/>").css(pluginVersionStyle).addClass("plugin-version");

    this._plugin.append(this._label);
    this._plugin.append(this._version);
    this._label.append(this._descriptor["label"]);

    this._versionNumbers = new Array();
    var i,l;
    l = this._versions.length;
    for(i=0;i<l;i++)
    {
       this._versionNumbers.push(this._versions[i]["version"]);
    }

    this._selector = new ali.DropDown("btn-xs", "btn-success", this._versionNumbers, this._lastVersion, null);

    this._version.append("Ver. ");
    this._version.append(this._selector.dropDown);

    this.clear = function()
    {
       this._plugin.remove();
    }
    Object.defineProperties(this,{
        plugin : {
            get : function()
            {
               return this._plugin;
            }
        }
    });
}
