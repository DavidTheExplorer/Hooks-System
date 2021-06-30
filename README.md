# No more static methods.
People 99% of the time use _static_ methods or Bukkit's nasty ways to communicate with other plugins.\
This library manages your plugin\'s hooks using OOP - which massively reduces your code, and allows useful features when your plugin depends on multiple plugins.

## Benefits
* Short English API.
* Auto check whether the plugin you depend on is exists in the server; If not, a special handler runs.
* Code reduction, even if you only depend on **1** plugin.
* Hooks Abstraction - Multiple hooks classes can implement an interface, and you can get the implementation that's on the server.
* Supports Bukkit Services API.

## Show me the magic!
```java
@Override
public void onEnable()
{
    HookService hookService = HookSystemAPI.createHookService(this);
    
    //Register LuckPerms' hook. LuckPerms isn't on the server? disable the plugin.
    hookService.register(new LuckPermsHook(), disablePlugin(this)); 

    //later, do something if LuckPerms is on the server.
    hookService.query(LuckPermsHook.class).isPresent(lpHook -> System.out.println(lpHook.groupExists("owner"));
}
```

## The Hook Class
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

## Hooks Registration
First of all, grab your plugin\'s **HookService**:
```java
//in your onEnable()
HookService hookService = HookSystemAPI.createHookService(this);
```
You:

* Want to register a hook and close your plugin if the plugin is not on the server?
```java
hookService.register(new WorldGuardHook(), disablePlugin(this));
```

* Want to notify the console, and then close the plugin?
```java
hookService.register(new WorldGuardHook(), 
   byOrder(
      logToConsole(this, "cannot function with WorldGuard! so it won't use it.")),
       disablePlugin(this)
   );
```

_byOrder()_, _logToConsole()_, and _disablePlugin()_ were statically imported from **MissingHandlersFactory**, which offers common handlers.\
They run only if the plugin is not on the server!

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.
