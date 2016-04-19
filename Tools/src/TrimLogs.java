import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TrimLogs {

	public static String regex = "(\\S+)?\\s*(critical|error|warning|alert|notice|info|debug|err|crit|CRIT)?(?:\\s)?(?:(kernel):)?(?:\\s)?(?:\\b(apd|bigd|bigpipe|bigstart|respawn|sod|superparse|bigdbd|dcc|gtmd|(tmm\\d*)|mcpd|overdog|bcm56xxd|chmand|b|alertd|promptstatusd|system_check|tmsh|diskmonitor|su-l|lacpd|lind|csyncd|date|msgbusd|msgbusd.sh|vxland|big3d|zxfrd|sflow_agent|scriptd|iprepd|mprov|statsd|tmrouted|tmipsecd|rmonsnmpd|load_config_files|merged|perl|vdi|websso\\S+?|localdbmgr|eca|tsconfd|g_server_rpc_handler_async.pl|bigstart_wait|iControlPortal.cgi|tcpdump|sSMTP|ts_configsync.pl|mysqlhad|httpd_sam|increase_entropy|evrouted|istatsd|zrd|ControlPlane.CorrelationEngine|icrd|dnscached|updatecheck|ant_server.sh|apmd|audit_forwarder|find-activate|eventd|devmgmtd)\\b(?:\\(pam_audit\\))?(?:\\[(\\d+)\\]*)?:\\s*(.*)|(?:: ((Could not make connection with MCP), err (\\S+))))";
	public static List<String> subMessages;
	
	public static void main(String args[]) {
		
		String filePath = "C:\\Users\\Thong La\\Desktop\\FastTrak\\F5_BigIP\\sort_uniq_1_allraw.txt";
		String line = "";
		FileReader fr = null;
		BufferedReader br = null;
		subMessages = new ArrayList<String>();
		boolean isDuplicate = true;
		
		try {
			fr = new FileReader(filePath);;
			br = new BufferedReader(fr);;
			String nextSubMessages = "";
			while ((line = br.readLine()) != null) {
				nextSubMessages = parseLine(line);
				if (!subMessages.isEmpty()) {
					
					isDuplicate = false;
					for (String subMessage : subMessages) {
						if (nextSubMessages.equals(subMessage)) {
							isDuplicate = true;
						}
					}
					if (!isDuplicate) {
						subMessages.add(nextSubMessages);
						System.out.println(line);
					}
				} else {
					subMessages.add(nextSubMessages);
					System.out.println(line);
				}
			}
			/*Collections.sort(subMessages);
			System.out.println("\n\n\n\n\n\n\n::::::: Submessages ::::::::");
			for (int i=0; i<subMessages.size();i++) {
				System.out.println(subMessages.get(i));
			}*/
			br.close();
			fr.close();
		} catch (IOException ex) {
			System.err.format("IOException: %s%n", ex);
		} 
	}
	
	public static String parseLine(String inputLine) {
		String message = "";
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(inputLine);
		if (matcher.find()) {
			message = matcher.group(7);
		}
		return message;
	}
	
}
