package main.java.net._null.MrClassy.utils;

import Consumer;
import MySQLConnectionPool;
import Updater;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;



public class Connection {
	private final Queue<Row> queue = new LinkedBlockingQueue<Row>();
	final Connection conn = this.getConnection();
	
	public void onInit() {
		try {
			updater = new Updater(this);
			Config.load(this);
			getLogger().info("[LogBlock] Connecting to " + user + "@" + url + "...");
			pool = new MySQLConnectionPool(url, user, password);
			final Connection conn = getConnection();
			if (conn == null) {
				noDb = true;
				return;
			}
			conn.close();
			if (updater.update())
				load(this);
			updater.checkTables();
		} catch (final NullPointerException ex) {
			getLogger().log(Level.SEVERE, "[LogBlock] Error while loading: ", ex);
		} catch (final Exception ex) {
			getLogger().severe("[LogBlock] Error while loading: " + ex.getMessage());
			errorAtLoading = true;
			return;
		}
		consumer = new Consumer(this);
	}
	
	public void run()
	{
		
		
	}
	
	public void writeToFile() throws FileNotFoundException {
		final long time = System.currentTimeMillis();
		final Set<String> insertedPlayers = new HashSet<String>();
		int counter = 0;
		new File("plugins/LogBlock/import/").mkdirs();
		PrintWriter writer = new PrintWriter(new File("plugins/LogBlock/import/queue-" + time + "-0.sql"));
		while (!queue.isEmpty()) {
			final Row r = queue.poll();
			if (r == null)
				continue;
			for (final String player : r.getPlayers())
				if (!playerIds.containsKey(player) && !insertedPlayers.contains(player)) {
					writer.println("INSERT IGNORE INTO `lb-players` (playername) VALUES ('" + player + "');");
					insertedPlayers.add(player);
				}
			for (final String insert : r.getInserts())
				writer.println(insert);
			counter++;
			if (counter % 1000 == 0) {
				writer.close();
				writer = new PrintWriter(new File("plugins/LogBlock/import/queue-" + time + "-" + counter / 1000 + ".sql"));
			}
		}
		writer.close();
	}
	
	private static interface Row
	{
		String[] getInserts();

		String[] getPlayers();
	}
	
	
	
}
