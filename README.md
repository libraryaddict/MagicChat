MagicChat

Quite simply, this is a plugin which does stuff when you type into chat.

A example is. And its already setup..

"By the power invested in me

"I command the sky of the blue world!"

"Turn to night!"

This turns the world to night.

Looking in the config, you can change up the wording, the spells.

And even make your own!

If you are a java developer, you can use the api, MagicApi to register new spells.

You can also use the plugin skript to make new spells!

I've no idea how to use that. But it works already! Not so sure about the args however.

Example:

> on spell cast:

>  broadcast spell
  
>  broadcast args (Should really be a array, but its a string I think)
  
You can use the args to match stuff such as

"strike %arg% with lightning!"

where the %arg% is replaced with someones name.

And lightning strikes them when someone says that.

I include 8 spells as part of the plugin, its easy to add more!
Just have your class extend 'Spell' and implement the

public void invokeSpell(Player player, String[] args) {}

This plugin is mainly for flair.
In a production server you could make a spell

"Turn %world% to night"

Then say it whenever you please!

Or even

"Let everyone have diamonds!"

And everyone has.. diamonds..

So as you can imagine. Its rather useful.

Not just for fun. Well. Dramatic anyhows.

But then. Drama is what magic is always about!