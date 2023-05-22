package com.atilaacedo.pdffacilitador;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PdfMain {

	public static void main(String[] args) {
		PdfMain p = new PdfMain();

		p.criarUsuarios();

	}

	public Map<String, String> lerExcel(String caminho, int index) {

		Map<String, String> usuarios = new HashMap<String, String>();

		try {
			FileInputStream plan = new FileInputStream(new File(caminho));

			XSSFWorkbook workBook = new XSSFWorkbook(plan);

			XSSFSheet sheetUsuarios = workBook.getSheetAt(index);

			Iterator<Row> rowIterator = sheetUsuarios.iterator();

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				String nome = "";
				String sala = "";
				while (cellIterator.hasNext()) {

					Cell cell = cellIterator.next();
					if (cell.getColumnIndex() == 0 && !(cell.getStringCellValue().equals("Nome"))) {
						nome = cell.getStringCellValue();
					} else if (cell.getColumnIndex() == 1 && !(cell.getStringCellValue().equals("Setor"))) {
						sala = cell.getStringCellValue();
					}

				}
				usuarios.put(nome, sala);

			}

			plan.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return usuarios;
	}

	public int localizarSala(String des_sala) {
		DAOPdf d = new DAOPdf();
		Map<String, Integer> mapSalas = d.getSalas(14);

		if (mapSalas.containsKey(des_sala)) {
			return mapSalas.get(des_sala);
		} else {
			d.adicionarSala(des_sala, 14);
			mapSalas = d.getSalas(14);

			return mapSalas.get(des_sala);
		}

	}

	public void criarUsuarios() {

		
		int c = 21;
		for (int i = 0; i < 18; i++, c++) {

			String pathExcel = "D:/Atila/Medkey/SACA.xlsx" ;
			Map<String, String> usuarios = lerExcel(pathExcel, i);
			DAOPdf d = new DAOPdf();
			
			for (Map.Entry<String, String> u : usuarios.entrySet()) {
				String nome = u.getKey();
				if (nome.length() >= 1) {
					String[] aux = nome.split(" ");
					String login = aux[0] + " " + aux[aux.length - 1];
					int cod_sala = localizarSala(u.getValue());
					String sts_ativo = "A";
					String cod_empresa = "2";
					String email = aux[0].toLowerCase() + "." + aux[aux.length - 1].toLowerCase() + "@ints.org.br";
					int cod_papel = c;
					String senha = "Ints%saca2023" +cod_papel;
					d.adicionarUsuario(login, senha, nome, cod_sala, sts_ativo, cod_empresa, email, cod_papel);
				}
			}

		}
	}

}
