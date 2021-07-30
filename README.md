# No more static methods.
People 99% of the time use _static_ methods or Bukkit's nasty ways to access other plugins' APIs.\
This library manages your plugin\'s hooks using OOP - which simplifies and reduces your code, and allows useful features when your plugin depends on multiple plugins.

## Show me the Magic!
inside onEnable, You:
* Want to register a hook, but disable your plugin if the hook's plugin is missing?
```java
//Similarly to Collectors.toList(), you can(and are supposed to) statically import disablePlugin()
hookService.register(new WorldGuardHook()).orElse(disablePlugin(this)); 
```
* Want to notify the console, and then close your plugin?
```java
hookService.register(new LuckPermsHook()).orElse(byOrder(
	logToConsole(this, "Closing because LuckPerms is missing!"),
	disablePlugin(this))); 
```
And here's the Full onEnable():
```java
@Override
public void onEnable()
{
    HookService hookService = HookSystemAPI.getService(this);
    
    hookService.register(new WorldGuardHook()).orElse(disablePlugin(this));
    hookService.register(new LuckPermsHook()).orElse(byOrder(logToConsole(this, "Closing because LuckPerms is missing!"), disablePlugin(this))); 
   
   
    //later, do something if LuckPerms is on the server.
    hookService.query(LuckPermsHook.class).isPresent(lpHook -> System.out.println(lpHook.groupExists("owner"));
}
```

## Benefits
* Short English API.
* Auto check whether the plugin you depend on is exists in the server; If not, a special handler runs.
* Code reduction, even if you only depend on **1** plugin.
* Hooks Abstraction - Multiple hooks classes can implement an interface, and you can get the implementation that's on the server.
* Supports Bukkit Services API.

## The Hook Class
The main interface is PluginHook, but the recommended class to use is **AbstractPluginHook**.
```java
public class LuckPermsHook extends AbstractPluginHook
{
	private LuckPerms luckPerms;
	
	public LuckPermsHook()
	{
		super("LuckPerms"); //set which plugin this hook represents, DO NOT access the API here!
	}
	
	@Override
	public void init() throws Exception
	{
		//This method runs only if LuckPerms is on the server - so it's safe to access its API.
    
		this.luckPerms = queryProvider(LuckPerms.class); //getting the LuckPerms instance from Bukkit's Services API
	}
  
        //say NO to static!
	public boolean groupExists(String groupName) 
	{
		return this.luckPerms.getGroupManager().getGroup(groupName) != null;
	}
}
```
The idea is to encapsulate the plugin's API behind public methods.\
Visit the [Example Plugin](https://github.com/DavidTheExplorer/Hooks-System/blob/master/src/dte/hooksystem/exampleplugin/hooks/WorldGuardHook.java) to see many more examples.

# Important
In the Examples: _byOrder()_, _logToConsole()_, and _disablePlugin()_ were statically imported from **MissingHandlersFactory**, which offers many commonly used handlers.\
It's very recommended to always do so in order to maintain your code concise.

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.
