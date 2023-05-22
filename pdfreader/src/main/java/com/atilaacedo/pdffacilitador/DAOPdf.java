package com.atilaacedo.pdffacilitador;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.atilaacedo.pdffacilitador.model.Sala;

public class DAOPdf {
    Properties prop = getProperties();

    /** The driver. */
    private String driver = "com.mysql.cj.jdbc.Driver";

    final String url = prop.getProperty("banco.url");
    final String user = prop.getProperty("banco.usuario");
    final String senha = prop.getProperty("banco.senha");

    private static Properties getProperties() {
        Properties pro = new Properties();
        String caminho = "/conexao.properties";
        try {
            pro.load(DAOPdf.class.getResourceAsStream(caminho));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pro;
    }

    private Connection conectar() {
        Connection con = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, senha);
            return con;
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }

    public Map<String, Integer> getSalas(int cod_sala) {
        Map<String, Integer> mapSalas = new HashMap<String, Integer>();
        Sala auxSala = new Sala();
        String jqpl = "select * from sala where cod_local = ?";
        

        try {
            Connection con = conectar();
            PreparedStatement psmt = con.prepareStatement(jqpl);
            psmt.setInt(1, cod_sala);
            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {
               auxSala.setCod_sala(rs.getInt(1));
               auxSala.setCod_local(rs.getInt(2));
               auxSala.setDes_sala(rs.getString(3));
               
               mapSalas.put(auxSala.getDes_sala(), auxSala.getCod_sala());
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mapSalas;
    }
    
    public void adicionarSala(String des_sala, int cod_local) {
    	String jqpl = "insert into sala (cod_local, des_sala) values(?,?)";
    	try {
			Connection connection = conectar();

			PreparedStatement psmt = connection.prepareStatement(jqpl);
			psmt.setInt(1,cod_local);
			psmt.setString(2,des_sala);
			psmt.executeUpdate();

			connection.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }
    
    
    public void adicionarUsuario(String login, String senha, String nome, int cod_sala, String sts_ativo, String cod_empresa, String email, int cod_papel) {
    	String jqpl = "insert into usuario (login, senha, des_usuario, cod_sala, sts_ativo, cod_empresa, email, cod_papel) values(?,?,?,?,?,?,?,?)";
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
    
}
