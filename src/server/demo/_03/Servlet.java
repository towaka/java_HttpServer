package server.demo._03;
/**
 * 抽象为一个父类
 * @author fukur
 *
 */
public abstract class Servlet {
	public void service(OwnRequest req,OwnResponse rep) throws Exception{
		this.doGet(req, rep);
		this.doPost(req, rep);
	}
	
	public abstract void doGet(OwnRequest req,OwnResponse rep) throws Exception;
	public abstract void doPost(OwnRequest req,OwnResponse rep) throws Exception;
}
