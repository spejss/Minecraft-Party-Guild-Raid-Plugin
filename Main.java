
package com.spejs.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import code.husky.mysql.MySQL;



public class test extends JavaPlugin {
	MySQL guild = new MySQL(plugin, ip, port, dbname, dbpsw, psw);
	Connection c = null;
	public static test plugin;

	public void onEnable(){
		c = guild.openConnection();
		this.getLogger().info("RPG-ADVENTURER MODE IS MOTHAPHUKKIN ON, YOU DIG IT?");
		this.getLogger().info("DZIALA POLACZENIE Z MYSQL");
		}
	
	public void onDisable(){
		this.getLogger().info("these mutaphukkas shot me down :(");
		}

// @SuppressWarnings("deprecation")
@SuppressWarnings({ })
public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
	
	/** EASTER EGGS **/
	String easter_egg1 = "siemanko";
	
	/** GUILD COMMANDS **/
	String create_guild = "cguild";
	String guild_invite = "ginvite";
	String guild_invite_accept = "guildaccept";
	//String guild_invite_decline = "gdecline";
	String my_guild = "myguild";
	String get_members = "gmembers";
	String kick_member = "gkick";
	String leave_guild = "leaveguild";
	String disband_guild = "disbandguild";

	/** PARTY COMMANDS **/
	String create_party = "cparty";
	//String disband_party = "disbandparty";
	String leave_party = "leaveparty";
	//String kick_player_from_the_party = "kparty";
			
	/** RAID COMMANDS **/
	
	
	
	
	/** %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%	EASTER EGGS	%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% **/	
	//100% working
	if(cmd.getName().equalsIgnoreCase(easter_egg1)){
			sender.sendMessage(ChatColor.BLUE + "Yoooooo" + " " + ChatColor.RED + sender.getName());
			return true;
			} 
	
	/** %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%	GUILD SYSTEM	%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% **/
	//CGUILD 100% WORKING
	 else if(cmd.getName().equalsIgnoreCase(create_guild)){
			if(args.length >= 0){
				try{
					Statement statement = c.createStatement();
					ResultSet senderGuild = statement.executeQuery("SELECT * FROM guild WHERE members = '" + sender.getName() + "';");
						if(!senderGuild.next()){
							statement.executeUpdate("INSERT INTO `mcmysql`.`guild` (`name`, `leader`, `members`) VALUES ( '" + String.valueOf(args[0])  +"', '"+sender.getName()+"', '"+ sender.getName() + "');");
							sender.sendMessage(ChatColor.AQUA + "Du hast die Gilde " + ChatColor.LIGHT_PURPLE + String.valueOf(args[0]) + ChatColor.AQUA + " erstellt.");
							return true;
						}else{
							sender.sendMessage(ChatColor.RED + "You arleady are in a guild");//Player player = (Player) sender;
							return false;
						}
				}catch(SQLException cs){
						this.getLogger().info("CREATE GUILD: I AM THE ONE WHO KNOCKS");
						}
				}
			} 


	//MYGUILD 100% WORKING
	else if(cmd.getName().equalsIgnoreCase(my_guild)){
			if(args.length >= 0){
				try{
					String gildia = null;
					Statement statement5 = c.createStatement();
					ResultSet senderGuild = statement5.executeQuery("SELECT name FROM guild WHERE members = '" + sender.getName() + "';");
						if(senderGuild.next()){
							gildia = senderGuild.getString(1);
							String senders_guild = senderGuild.getString(1);
							if(senders_guild != null){
								sender.sendMessage(ChatColor.AQUA + "You are in the guild: " + ChatColor.LIGHT_PURPLE + gildia);
								this.getLogger().info(sender.getName() + " is in the guild" + gildia);
								return true;
								}
						}else{
							sender.sendMessage(ChatColor.AQUA + "YOU ARE GILDLOS ");
							this.getLogger().info("false");
							return false;
						}
						}catch(SQLException mg){
							this.getLogger().info("MY GUILD: SYSTEM IS NOT WORKING");
					}
				}
			}

	
	
	//GINVITE 100% WORKING
//TODO: ADD /ACCEPT FOR THE INVITED PLAYER OR /DECLINE FOR INVITES CREATE NEW TABLE INVITES WITH LEADER, invited AND 
	else if(cmd.getName().equalsIgnoreCase(guild_invite)){
		if(args.length >= 0){
			try{
				String gildia = null;
				Statement statementFG = c.createStatement();
				ResultSet guild = statementFG.executeQuery("SELECT name FROM guild WHERE leader ='" + sender.getName() + "';");
				if(guild.next()){
					gildia = guild.getString(1);
					this.getLogger().info(sender.getName() + " is in the guild: " + gildia);
					try{
						Statement statementIG = c.createStatement();
						ResultSet targetIsInGuild = statementIG.executeQuery("SELECT name FROM guild WHERE members ='" + String.valueOf(args[0]) + "';");
						if(targetIsInGuild.next()){
							sender.sendMessage(ChatColor.RED + "Target is already in a guild");
						}else{
							try{
								Statement statementone = c.createStatement();
								ResultSet alreadyinvited = statementone.executeQuery("SELECT * FROM guildinv WHERE invited='" + String.valueOf(args[0] + "';"));
								if(alreadyinvited.next()){
									sender.sendMessage(ChatColor.RED + "ALREADY INVITED");
									
								}else{
									Statement statementI = c.createStatement();
									statementI.executeUpdate("INSERT INTO guildinv (leader, invited) VALUES ( '" + sender.getName() +"', '"+ String.valueOf(args[0]) + "');");
									sender.sendMessage(ChatColor.LIGHT_PURPLE + String.valueOf(args[0]) + " has been invited to the guild");
									
								}
						
								
							}catch(SQLException g){
								this.getLogger().info("targetIsInGuild: SYSTEM IS NOT WORKING PROPERLY");
							}
							
						}
					}catch(SQLException y){
						this.getLogger().info("");
					}
					
				}else{
					this.getLogger().info("GUILD_INVITE: SYSTEM IS NOT WORKING PROPERLY");
				}
			}catch(SQLException x){
				
			}
		}
		}

			

	else if(cmd.getName().equalsIgnoreCase(guild_invite_accept)){
		try{
			Statement statementGIA = c.createStatement();
			ResultSet invited = statementGIA.executeQuery("SELECT * FROM guildinv WHERE invited ='" + sender.getName() + "';");
			this.getLogger().info("ISINVITED QUERY IS WORKING PROPERLY " + sender.getName() );
			if(invited.next()){
				try{
					String leader_name = null;
					Statement statementlider = c.createStatement();
					ResultSet lider = statementlider.executeQuery("SELECT `leader` FROM `guildinv` WHERE `invited`='" + sender.getName() + "';");
					leader_name = lider.getString(1);
					this.getLogger().info("LEADER: " + leader_name);
				}catch(SQLException two){
					this.getLogger().info("SELECT LEADER IS NOT WORKING PROPERLY");
				}
				
				
			}
			
		}catch(SQLException one){
			this.getLogger().info("GUILD INVITE ACCEPT SYSTEM IS NOT WORKING PROPERLY");
		}
	}

				
	//get members 100% working
	else if(cmd.getName().equalsIgnoreCase(get_members)){
		try{
			String leader = null;
			Statement statementGM = c.createStatement();
			ResultSet myGuildLeader = statementGM.executeQuery("SELECT leader FROM guild WHERE members = '" + sender.getName() + "';");
			if(myGuildLeader.next()){
				leader = myGuildLeader.getString(1);
				this.getLogger().info("FIND LEADER: SYSTEM IS WORKING " + leader);
				ResultSet guildMembers = statementGM.executeQuery("SELECT members FROM guild WHERE leader='" + leader + "';");
				sender.sendMessage(ChatColor.LIGHT_PURPLE + "MEMBERS: ");
				while(guildMembers.next()){
					String members = guildMembers.getString(1);
					sender.sendMessage(ChatColor.LIGHT_PURPLE + members);
					
				}
			}
		}catch(SQLException memba){
			this.getLogger().info("GET MEMBERS: SYSTEM IS NOT WORKING PROPERLY");
		}
	}
	
	
	//LEAVEGUILD 100% WORKING
	else if(cmd.getName().equalsIgnoreCase(leave_guild)){
		try{
			Statement statementMG = c.createStatement();
			ResultSet myGuild = statementMG.executeQuery("SELECT * FROM guild WHERE members = '" + sender.getName() + "';");
			if(!myGuild.next()){
				sender.sendMessage(ChatColor.DARK_RED + "You are not in a guild");
			}else{
				Statement statementLG = c.createStatement();
				statementLG.executeUpdate("DELETE FROM guild WHERE members = '" + sender.getName() + "';" );
				sender.sendMessage(ChatColor.DARK_AQUA + "You have left the guild");
			}
		}catch(SQLException lg){
			this.getLogger().info("LEAVE GUILD: SYSTEM IS NOT WORKING (Hartz IV)");
			}
		}

	//kick from the guild 100% working
	else if(cmd.getName().equalsIgnoreCase(kick_member)){
		if(args.length >= 0){
			try{
			String name = String.valueOf(args[0]); 
			String leader = sender.getName();
			Statement statementKM = c.createStatement();
			ResultSet isSenderLeader = statementKM.executeQuery("SELECT * FROM guild WHERE leader = '" + leader+ "';");
			this.getLogger().info(name);
			if(isSenderLeader.next()){
				this.getLogger().info("IsSenderLeader: SYSTEM IS WORKING, LEADER IS " + leader + " AND KICKED SHOULD BE " + name );
				Statement statementK = c.createStatement();
				statementK.executeUpdate("DELETE FROM guild WHERE members = '" + name + "';");
				sender.sendMessage(ChatColor.DARK_BLUE + name + " has been kicked from the guild");
				this.getLogger().info("DELETE FROM GUILD: SYSTEM IS WORKING");
			}else{
				sender.sendMessage(ChatColor.DARK_RED + "You are not the leader");
			}
			
			
			}catch(SQLException km){
				this.getLogger().info("KICK MEMBER: SYSTEM IS NOT WORKING");
				
			}
		}
	}
	
	//disband guild 100% working
	else if(cmd.getName().equalsIgnoreCase(disband_guild)){
		try{
			Statement statementMG = c.createStatement();
			ResultSet senderGuild = statementMG.executeQuery("SELECT * FROM guild WHERE members = '" + sender.getName() + "';");
			if(!senderGuild.next()){
				sender.sendMessage(ChatColor.DARK_RED + "You are not in a guild");
			}else{
				try{
					Statement statementLeader = c.createStatement();
					ResultSet isLeader = statementLeader.executeQuery("SELECT * FROM guild WHERE leader = '" + sender.getName() + "';");
					if(!isLeader.next()){
						sender.sendMessage(ChatColor.DARK_RED + "You are not the leader");
					}else{
						Statement statementLG = c.createStatement();
						statementLG.executeUpdate("DELETE FROM guild WHERE leader = '" + sender.getName() + "';" );
						sender.sendMessage(ChatColor.DARK_AQUA + "You have disbanded the guild");
					}
				}catch(SQLException dg){
					this.getLogger().info("DISBAND GUILD: DELETE MODUL IS NOT WORKING");
					}
				}
			}catch(SQLException lg){
			this.getLogger().info("DISBAND GUILD: SYSTEM IS NOT WORKING (Hartz IV)");
			}
		}
		/** %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%	PARTY SYSTEM	%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% **/
	else if(cmd.getName().equalsIgnoreCase(create_party)){
			try{
				Statement statementCP = c.createStatement();
				ResultSet senderParty = statementCP.executeQuery("SELECT * FROM party WHERE members = '" + sender.getName() + "';");
				this.getLogger().info("GOT THE PARTY | ATEMPTING TO ADD TARGET TO PARTY");
				String leader = sender.getName();
					if(senderParty.next()){
						sender.sendMessage(ChatColor.RED + "Du bist schon in einer gruppe");
						}else{
						statementCP.executeUpdate("INSERT INTO `mcmysql`.`party` (`leader`, `members`) VALUES ( '" + leader +"', '"+ leader + "');");
						sender.sendMessage(ChatColor.AQUA + "Du hast die Gruppe " + ChatColor.AQUA + " erstellt.");
					}
			}catch(SQLException cs){
					this.getLogger().info("CREATE PARTY: SYSTEM IS NOT WORKING || but still...EVERYTHING IS AWESOME, EVERYTHING IS COOL WHEN YOU'RE PART OF THE TEAM, EVERYTHING IS AWESOMEEEEEEEEE");
					}
			}
		

	else if(cmd.getName().equalsIgnoreCase(leave_party)){
		try{
			Statement statementMP = c.createStatement();
			ResultSet myParty = statementMP.executeQuery("SELECT * FROM party WHERE members = '" + sender.getName() + "';");
			if(!myParty.next()){
				sender.sendMessage(ChatColor.DARK_RED + "You are not in a group");
			}else{
				Statement statementLP = c.createStatement();
				statementLP.executeUpdate("DELETE FROM party WHERE members = '" + sender.getName() + "';" );
				sender.sendMessage(ChatColor.DARK_AQUA + "You have left the group");
			}
		}catch(SQLException lg){
			this.getLogger().info("LEAVE PARTY: SYSTEM IS NOT WORKING (Hartz IV)");
			}
		}
	
	/**
	else if(cmd.getName().equalsIgnoreCase(disband_party)){
		try{
			Statement statementMP = c.createStatement();
			ResultSet myParty = statementMP.executeQuery("SELECT * FROM guild WHERE members = '" + sender.getName() + "';");
			if(!myParty.next()){
				sender.sendMessage(ChatColor.DARK_RED + "You are not in a group");
				}else{
				Statement statementPL = c.createStatement();
				ResultSet partyLeader = statementPL.executeQuery("SELECT * FROM guild WHERE leader = '" + sender.getName() + "';");
				if(!partyLeader.next()){
					sender.sendMessage(ChatColor.DARK_GRAY + "You are not the leader of the group");
					
				}else if(!partyLeader.next()){
					Statement statementDG = c.createStatement();
					statementDG.executeUpdate("DELETE FROM party WHERE leader = '" + sender.getName() + "';" );
					sender.sendMessage(ChatColor.DARK_AQUA + "Your group has been disbanded");
				}
				
			else{
				if(!partyLeader.next()){
					sender.sendMessage(ChatColor.DARK_GRAY + "You are not the leader of the group");
					
				}else if(!partyLeader.next()){
					Statement statementDG = c.createStatement();
					statementDG.executeUpdate("DELETE FROM party WHERE leader = '" + sender.getName() + "';" );
					sender.sendMessage(ChatColor.DARK_AQUA + "Your group has been disbanded");
				}
				
			}
				}
		}catch(SQLException lg){
			this.getLogger().info("DISBAND PARTY: SYSTEM IS NOT WORKING (Hartz IV)");
			}
		}
		**/
		
		
		/** %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%	RAID SYSTEM	%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% **/			
		/** }else if(cmd.getName().equalsIgnoreCase("guild_invite") && args.length > 1){
			Player player = Bukkit.getPlayerExact(args[1]);
			try{
			if(player != null && sender != player){
				c = guild.openConnection();
				Statement statement = c.createStatement();
				ResultSet res = statement.executeQuery("SELECT * FROM guild WHERE name = '" + args[1] + "';" );
				res.next();
				if(res.getString(args[1]) == null){
					ResultSet senderGuild = statement.executeQuery("SELECT * FROM guild WHERE name = '" + sender.getName() + "';" );
					if(senderGuild != null){
						statement.executeQuery(" INSERT INTO guild ('name', 'members') VALUES (' " + senderGuild + "' ,'"+ args[1] +"'"); 
						sender.sendMessage(ChatColor.GREEN + "Player " + args[1] + " has been added to the guild");
						}
				}else{
					sender.sendMessage(ChatColor.DARK_RED + args[1] + " is already in a guild");
				}
			//GUILD INVITE AND GUILD KICK TODO: ADD MECHANISMS
			sender.sendMessage(ChatColor.GREEN + sender.getName() + " " + "has invited" + " " + ChatColor.AQUA + args[1]);
			return true;
			}else if(sender == player){
				sender.sendMessage(ChatColor.RED + sender.getName() + " ,You can't invite yourself");
				return false;
			}else if(player == null){
				sender.sendMessage(ChatColor.YELLOW + "Player " + args[1] + "is " + ChatColor.RED + " offline ");
				return false;
			}
			}catch(SQLException ex){
				this.getLogger().info("TEST PLUGIN ERROR");
			}
			
			
		
			/**
	}else if(cmd.getName().equalsIgnoreCase("gkick")){
			sender.sendMessage(ChatColor.RED + args[1] + " " + "has been kicked by " + sender.getName());
			return true;
	}else if(cmd.getName().equalsIgnoreCase("pinvite") && args.length == 2){
			//PARTY INVITE AND PARTY KICK || MAX. 1 PARTY LEADER + 4 MEMBERS TODO: ADD MECHANISMS
			sender.sendMessage(ChatColor.GREEN + sender.getName() + " " + "has invited" + " " + ChatColor.AQUA + args[1]);
			return true;
	}else if(cmd.getName().equalsIgnoreCase("pkick")){
			sender.sendMessage(ChatColor.RED + args[1] + " " + "has been kicked from the party" + " by " + sender.getName());
			return true;
			//RAID INVITE AND RAID KICK || MAX. 1 LEADER + 9 MEMBERS TODO: ADD MECHANISMS
	}else if(cmd.getName().equalsIgnoreCase("rinvite") && args.length == 2){
			sender.sendMessage(ChatColor.GREEN + sender.getName() + " " + "has invited" + " " + ChatColor.AQUA + args[1]);
			return true;
	}else if(cmd.getName().equalsIgnoreCase("rkick")){
			sender.sendMessage(ChatColor.RED + args[1] + " " + "has been kicked from the raid by " + sender.getName());
			return true; **/

	return false;
}

}









