# pixeldungeon
Clone of Pixel Dungeon (https://github.com/watabou/pixel-dungeon/).

## How to Play

I'm not giving you pre-compiled stuff or anything like that. (Maybe I will do that sometime later.) Compile the code yourself by running `python3 compiler.py`. It will automatically run the generated code once, but if you want to run it again, you can do `java -jar compiled.jar`.

Use the QWEADZXC keys to move around. (Look at your keyboard. You can move in all 8 directions.) Pressing S sleeps for 1 turn. You can look around. There's nothing really interesting yet. (And yet I already have 27 Java files! This is a big project.)

There's one enemy, who will pathfind (badly) towards you once woken up. It doesn't have very much health; you can kill it easily by walking into it.

You have an inventory, which starts out containing one item. There is another item randomly placed on the level. You can pick it up by walking into it.