package excelbanco;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import excelbanco.dao.ExcelDAO;


public class ReadExcelUsuarios {
	
	public static void main(String[] args) {
		ReadExcelUsuarios p = new ReadExcelUsuarios();

		p.criarUsuarios();

	}

	public Map<Map<String, String>, String> lerExcel(String caminho, int index) {

		Map<Map<String, String>, String> usuarios = new HashMap<Map<String, String>, String>();

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
				String cpf = "";
				Map<String, String> nomeCpf = new HashMap<String, String>();
				while (cellIterator.hasNext()) {

					Cell cell = cellIterator.next();
					if (cell.getColumnIndex() == 0 && !(cell.getStringCellValue().equalsIgnoreCase("Nome"))) {
						nome = cell.getStringCellValue();
					} else if (cell.getColumnIndex() == 1 && !(cell.getStringCellValue().equalsIgnoreCase("CPF"))) {
						cpf = cell.getStringCellValue();
					}else if (cell.getColumnIndex() == 2 && !(cell.getStringCellValue().equalsIgnoreCase("SETOR"))) {
						sala = cell.getStringCellValue();
					}
					
					
					nomeCpf.put(nome, cpf);
				}
				usuarios.put(nomeCpf, sala);

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
		ExcelDAO d = new ExcelDAO();
		Map<String, Integer> mapSalas = d.getSalasMap(31);

		if (mapSalas.containsKey(des_sala)) {
			return mapSalas.get(des_sala);
		} else {
			d.adicionarSala(des_sala, 31);
			mapSalas = d.getSalasMap(31);

			return mapSalas.get(des_sala);
		}

	}

	public void criarUsuarios() {


		int c = 7;
		for (int i = 0; i < 2; i++, c+=11) {

			String pathExcel = "C:\\Users\\Medkey\\Documents\\GEOQ_INTS/UPA-BROTAS.xlsx" ;
			Map<Map<String, String>, String> usuarios = lerExcel(pathExcel, i);
			ExcelDAO d = new ExcelDAO();

			for (Map.Entry<Map<String, String>, String> u : usuarios.entrySet()) {
				Map<String, String> nomeCpf = u.getKey();
				String nome = "";
				String cpf = "";
				for(Map.Entry<String, String> n : nomeCpf.entrySet()) {
					nome = n.getKey();
					cpf = n.getValue();
				}
				
				if (nome.length() >= 1) {
					String[] aux = nome.split(" ");
					String login = cpf;
					int cod_sala = localizarSala(u.getValue());
					String sts_ativo = "A";
					String cod_empresa = "2";
					String email = aux[0].toLowerCase() + "." + aux[aux.length - 1].toLowerCase() + "@ints.org.br";
					int cod_papel = c;
					String senha = "60fe4105aa4405b2b81929d6d2193dd5";
					d.adicionarUsuario(login, senha, nome, cod_sala, sts_ativo, cod_empresa, email, cod_papel);
				}
			}

		}
	}
}
