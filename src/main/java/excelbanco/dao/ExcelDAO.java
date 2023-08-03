package excelbanco.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import excelbanco.model.Sala;

public class ExcelDAO {
	
	
	private Properties prop = getProperties();
	
	private final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private final String URL = prop.getProperty("banco.url");
	private final String USER = prop.getProperty("banco.usuario");
	private final String SENHA = prop.getProperty("banco.senha");
	private List<Sala> listSalas; 
	
	private static Properties getProperties() {
		Properties prop = new Properties();
		
		String path = "/conexao.properties";
		
		try {
			prop.load(ExcelDAO.class.getResourceAsStream(path));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return prop;
	}
	
	private Connection conectar() {
		Connection con = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, SENHA);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	

    public void adicionarUsuario(String login, String senha, String nome, int cod_sala, String sts_ativo, String cod_empresa, String email, int cod_papel) {
    	String jqpl = "insert into usuario (des_login, des_senha, nom_usuario, cod_sala, sts_ativo, cod_empresa, des_email, cod_papel) values(?,?,?,?,?,?,?,?)";
    	try {
			Connection connection = conectar();

			PreparedStatement psmt = connection.prepareStatement(jqpl);
			psmt.setString(1,login);
			psmt.setString(2,senha);
			psmt.setString(3, nome);
			psmt.setInt(4, cod_sala);
			psmt.setString(5,sts_ativo);
			psmt.setString(6, cod_empresa);
			psmt.setString(7, email);
			psmt.setInt(8, cod_papel);
			psmt.executeUpdate();

			connection.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }
	
	public List<Sala> getSalas(int cod_local){
		
		listSalas = new ArrayList<Sala>();
		
		Sala auxSala = new Sala();
		
		String jpql = "SELECT * FROM sala WHERE cod_local = ?";
		
		try {
			Connection con = conectar();
			PreparedStatement psmt = con.prepareStatement(jpql);
			
			psmt.setInt(1, cod_local);
			
			ResultSet rs = psmt.executeQuery();
			
			while(rs.next()) {
				auxSala.setCodSala(rs.getInt(1));
				auxSala.setDesSala(rs.getString(2));
				auxSala.setCodLocal(rs.getInt(4));
				auxSala.setEmail(rs.getString(5));
				auxSala.setCod_usuario(rs.getInt(17));
				
				listSalas.add(auxSala);
			}
			
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return listSalas;
	}
	
	 public Map<String, Integer> getSalasMap(int cod_sala) {
	        Map<String, Integer> mapSalas = new HashMap<String, Integer>();
	        Sala auxSala = new Sala();
	        String jqpl = "select * from sala where cod_local = ?";


	        try {
	            Connection con = conectar();
	            PreparedStatement psmt = con.prepareStatement(jqpl);
	            psmt.setInt(1, cod_sala);
	            ResultSet rs = psmt.executeQuery();

	            while (rs.next()) {
	            	auxSala.setCodSala(rs.getInt(1));
					auxSala.setDesSala(rs.getString(2));
					auxSala.setCodLocal(rs.getInt(4));
					auxSala.setEmail(rs.getString(5));
					auxSala.setCod_usuario(rs.getInt(17));

	               mapSalas.put(auxSala.getDesSala(), auxSala.getCodSala());
	            }
	            con.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return mapSalas;
	    }
	
	 public void adicionarSala(String des_sala, int cod_local) {
	    	String jqpl = "insert into sala (cod_local, des_sala, sts_ativo, cod_empresa) values(?,?,?,?)";
	    	try {
				Connection connection = conectar();

				PreparedStatement psmt = connection.prepareStatement(jqpl);
				psmt.setInt(1,cod_local);
				psmt.setString(2, des_sala);
				psmt.setInt(3,1);
				psmt.setInt(4, 2);
				psmt.executeUpdate();

				connection.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
	    }
	
	public void atualizarSalas(int codSala, String email, int codUsuario) {
		   String jpql = "UPDATE sala SET email = ?, codUsuario = ? WHERE cod_sala = ?";
		
		try {
			Connection con = conectar();
			PreparedStatement psmt  = con.prepareStatement(jpql);
			
			psmt.setString(1, email);
			psmt.setInt(2, codUsuario);
			psmt.setInt(3, codSala);
			
			psmt.executeUpdate();
			
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	
	public int buscarUsuarioPeloNome(String nome) {
		String jpql = "SELECT * FROM usuario WHERE nom_usuario =?";
		
		
		try {
			Connection con = conectar();
			
			PreparedStatement psmt = con.prepareStatement(jpql);
			
			psmt.setString(1, nome);
			
			ResultSet rs = psmt.executeQuery();
			
			while(rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return 0;
	}
	
	public List<String> getUsuariosNomes(){
		List<String> usuariosNome = new ArrayList<String>();
		String jpql = "SELECT * FROM usuario where cod_sala";
		
		try {
			Connection con = conectar();
			PreparedStatement psmt = con.prepareStatement(jpql);
			
			ResultSet rs = psmt.executeQuery();
			
			while(rs.next()) {
				usuariosNome.add(rs.getString(5));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return usuariosNome;
	}
	
	public List<String> getUsuariosNomesPorLocal(int cod_local){
		List<String> usuariosNomePorLocal = new ArrayList<String>();
		String jpql = "SELECT * FROM usuario U INNER JOIN sala S ON U.cod_sala = S.cod_sala WHERE S.cod_local = ?";
		
		try {
			Connection con = conectar();
			PreparedStatement psmt = con.prepareStatement(jpql);
			psmt.setInt(1, cod_local);
			
			ResultSet rs = psmt.executeQuery();
			
			while(rs.next()) {
				usuariosNomePorLocal.add(rs.getString(5));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return usuariosNomePorLocal;
	}
	
	
	public void atualizarLoginUsuario(String nome, String cpf) {
		String novoLogin = cpf ;
		
		String jpql = "UPDATE usuario set des_login = ? where nom_usuario = ?";
		
		try {
			Connection con = conectar();
			
			PreparedStatement psmt = con.prepareStatement(jpql);
			psmt.setString(1, novoLogin);
			psmt.setString(2, nome);
			
			psmt.executeUpdate();
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void excluirUsuarioPeloNome(String nome) {
		String jpql = "DELETE FROM usuario where nom_usuario = ?";
		
		try {
			Connection con = conectar();
			
			PreparedStatement psmt = con.prepareStatement(jpql);
			psmt.setString(1, nome);
			
			psmt.executeQuery();
			con.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
