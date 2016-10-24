// ali - analise log interface
if(typeof(ali) == 'undefined') ali = function(){};
/**
    size - string for style, values: btn-lg, btn-sm, btn-xs
    style - values:
                    btn-default : white button,
                    btn-primary : blue button,
                    btn-success : green button,
                    btn-info    : blue button,
                    btn-warning : orange button,
                    btn-danger  : red button
    actions - array of available options
    currentAction - option which choose by default
    callback - function which will be choose after select new option
*/
ali.DropDown = function(size, style, actions, currentAction, callback)
{
    this._value = currentAction;
    this._callback = callback;
    this._options = new Array();
    this._dropdown = $("<div/>").addClass("btn-group");
    this._button = $("<button/>").addClass("btn " + size + " " + style + " dropdown-toggle");
    this._button.attr("data-toggle", "dropdown");
    this._button.attr("aria-haspopup", "true");
    this._button.attr("aria-expanded", "true");

    this.caret = $("<span/>").addClass("caret");

    this._button.append(currentAction);
    this._button.append(this.caret);

    this._optionsGroup = $("<ul/>").addClass("dropdown-menu dropdown-menu-right");

    this._dropdown.append(this._button);
    this._dropdown.append(this._optionsGroup);

    this.select = function(value)
    {
        this._button.empty();
        this._button.append(value);
        this._button.append(this.caret);
        this._value = value;
        if (this._callback)
        {
            this._callback(value);
        }
    }

    var i,l;
    l = actions.length;
    for (i=0;i<l;i++)
    {
        var opt = new ali.DropDownOption(actions[i], this);
        this._options.push(opt);
        this._optionsGroup.append(opt.option);
    }
    Object.defineProperties(this,{
        dropDown : {
            get : function()
            {
               return this._dropdown;
            }
        }
    });
/**
<div class="btn-group">
  <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
    Action <span class="caret"></span>
  </button>
  <ul class="dropdown-menu">
    <li><a href="#">Action</a></li>
    <li><a href="#">Another action</a></li>
    <li><a href="#">Something else here</a></li>
    <li><a href="#">Separated link</a></li>
  </ul>
</div>
**/
}

ali.DropDownOption = function(value, dropdown)
{
    this._myDropDown = dropdown;
    this._option = value;
    this._liBlock = $("<li/>");
    this._href = $("<a/>");
    this._liBlock.append(this._href);
    this._href.append(value);

    var self = this;
    this._href.on("mousedown", function()
    {
        self.selectOption();
    })

    this.selectOption = function()
    {
        this._myDropDown.select(this._option);
    }
    Object.defineProperties(this,{
        option : {
            get : function()
            {
               return this._liBlock;
            }
        }
    });

}