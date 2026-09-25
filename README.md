# DeathMessages

Paper plugin for customizable death, join, and quit messages with per-player toggles and blacklists.

## » About

Recolorizes death, join, and quit messages using MiniMessage, with separate settings for each message type. Players can control which messages they see, blacklist individual players, and configure additional filtering such as cooldowns and minimum playtime.

## » Features

- Custom MiniMessage colors for death, join, and quit messages
- Separate colors for death message components: main text, player, killer, and weapon
- Weapon hover tooltip with the item's full tooltip
- Per-player death message toggle, including own deaths
- Per-player join/quit message toggle
- Personal death message blacklist
- Global toggles for death, join, and quit messages
- Configurable death message cooldown
- Minimum playtime requirement for death messages
- Folia support

## » Commands

| Command | Aliases | Permission | Description |
| --- | --- | --- | --- |
| `/deathmessages reload` | `/dm reload` | `deathmessages.command.reload` | Reload config and user data |
| `/deathmessages toggle` | `/dm toggle` | `deathmessages.command.toggle` | Toggle death messages on/off |
| `/deathmessages blacklist <player>` | `/dm blacklist <player>` | `deathmessages.command.blacklist` | Add/remove a player from your blacklist |
| `/toggleconnectionmsg` | `/togglejoins` | `deathmessages.command.toggleconnectionmsg` | Toggle join/quit messages on/off |
| `/deathmessagestoggle` | `/dmtoggle`, `/dmt` | `deathmessages.command.toggle` | Shortcut for toggling death messages |

## » Permissions

| Permission | Default | Description |
| --- | --- | --- |
| `deathmessages.command.toggle` | `op` | Allows toggling death messages |
| `deathmessages.command.toggleconnectionmsg` | `op` | Allows toggling join/quit messages |
| `deathmessages.command.blacklist` | `op` | Allows blacklisting a player |
| `deathmessages.command.reload` | `op` | Allows reloading the plugin |

## » Configuration

### `UserData.yml`

Stored in the plugin data folder and created automatically when a player uses a toggle or blacklist command.

- `messages-enabled` — whether death messages are shown (`true` by default)
- `connection-messages-enabled` — whether join/quit messages are shown (`true` by default)
- `is-blacklisted` — whether the player is blacklisted (`false` by default)

## » Build

```bash
mvn clean package
