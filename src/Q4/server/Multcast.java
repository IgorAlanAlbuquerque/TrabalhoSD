package Q4.server;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Multcast {
	String mensagem;

	public Multcast(String mensagem) {
		// TODO Auto-generated constructor stub
		this.mensagem = mensagem;
		enviar();
	}
	private void enviar() {
		try {
			InetAddress addr = InetAddress.getByName("239.0.0.1");
			DatagramSocket ds = new DatagramSocket();
			
			byte[] b = mensagem.getBytes();
			DatagramPacket pkg = new DatagramPacket(b, b.length, addr, 12347);
			
			ds.send(pkg);
			ds.close();
			
		} catch (Exception e) {
			System.out.println("Nao foi possivel enviar a mensagem");
		}
	}

}
