package excelbanco;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import excelbanco.dao.ExcelDAO;

public class ReadExcelCPF {

	public static void main(String[] args) {
		ReadExcelCPF r = new ReadExcelCPF();
		/*
		 * Map<String, String> teste =
		 * r.lerExcel("C://Users//Medkey//Downloads/Lista_Def_Cpf.xlsx", 0);
		 * 
		 * for(Map.Entry<String, String> m : teste.entrySet()) {
		 * 
		 * if(m.getKey().equalsIgnoreCase("SILVANIA VIANA SOARES"))
		 * System.out.println(m.getKey() + m.getValue()); }
		 */

		r.atualizarUsuariosPorLocal(30, 3);
		
		/*Map<String, String> teste = r.lerExcel("C://Users//Medkey//Documents//GEOQ_INTS//INTS_CPF_Usuarios/Aracaju_CPF.xlsx", 0);
		
		for(Map.Entry<String, String> m : teste.entrySet()) {
			System.out.println(m.getKey() + " " + m.getValue().length());
		}*/
	}

	public HashMap<String, String> lerExcel(String caminho, int index) {

		HashMap<String, String> mapCPF = new HashMap<String, String>();

		try {
			FileInputStream plan = new FileInputStream(new File(caminho));

			XSSFWorkbook workBook = new XSSFWorkbook(plan);

			XSSFSheet sheetUsuarios = workBook.getSheetAt(index);

			Iterator<Row> rowIterator = sheetUsuarios.iterator();

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				String nome = "";
				String cpf = "";
				while (cellIterator.hasNext()) {

					Cell cell = cellIterator.next();
					if (cell.getColumnIndex() == 0 && cell.getRowIndex() !=0) {
						nome = cell.getStringCellValue();
						nome = nome.toLowerCase();
					} else if (cell.getColumnIndex() == 1 && cell.getRowIndex() !=0) {
						cpf = cell.getStringCellValue();
					}

				}
				mapCPF.put(nome, cpf);
			}

			plan.close();
			workBook.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mapCPF;
	}

	public void atualizarUsuariosPorLocal(int cod_local, int tamanho) {

		ExcelDAO dao = new ExcelDAO();

		List<String> nomeUsuarios = dao.getUsuariosNomesPorLocal(cod_local);

		String path = "C://Users//Medkey//Documents//GEOQ_INTS//INTS_CPF_Usuarios/Aracaju_CPF.xlsx";

		for (int i = 0; i < tamanho; i++) {
			if(i == 1) i = 2;
			HashMap<String, String> mapSetorCpf = lerExcel(path, i);
			
			for (int j = 0; j < nomeUsuarios.size(); j++) {

				if (mapSetorCpf.containsKey(nomeUsuarios.get(j).toLowerCase())) {
					
					/*if(mapSetorCpf.get(nomeUsuarios.get(j).toLowerCase()).length() == 0)
						dao.excluirUsuarioPeloNome(nomeUsuarios.get(j));
					else */
						dao.atualizarLoginUsuario(nomeUsuarios.get(j), mapSetorCpf.get(nomeUsuarios.get(j).toLowerCase()));
				} 

			}
		}

	}

	public void criarPlanilhaExcel(Set<String> usuariosNaoAdicionados) {
		String path = "C://Users//Medkey//Documents//GEOQ_INTS/UsuariosSemCPF.xlsx";

		XSSFWorkbook workBook = new XSSFWorkbook();

		XSSFSheet sheetUsuarios = workBook.createSheet("Usuarios Sem CPF");

		Iterator<String> usuarios = usuariosNaoAdicionados.iterator();

		int rowNum = 0;
		while (usuarios.hasNext()) {
			Row row = sheetUsuarios.createRow(rowNum++);
			Cell cellNome = row.createCell(0);
			cellNome.setCellValue(usuarios.next());
		}

		try {
			FileOutputStream out = new FileOutputStream(new File(path));
			workBook.write(out);
			out.close();
			System.out.println("Arquivo Excel criado com sucesso!");
			
			workBook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Arquivo não encontrado!");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Erro na edição do arquivo!");
		}

	}
	/*
	 * public void atualizarUsuariosComCpf() {
	 * 
	 * 
	 * ExcelDAO dao = new ExcelDAO();
	 * 
	 * String path = "C://Users//Medkey//Downloads/Lista_Def_Cpf.xlsx"; List<String>
	 * nomeUsuarios = dao.getUsuariosNomes(); Set<String> usuariosNaoAdicionados =
	 * new HashSet<String>(); List<String> mapeandoNaoCadastrados = new
	 * ArrayList<String>(); List<String> cadastrados = new ArrayList<String>();
	 * 
	 * for(int i = 0; i < 4;i++) { HashMap<String, String> mapSetorCpf =
	 * lerExcel(path, i);
	 * 
	 * 
	 * System.out.println("---------------------------------------------");
	 * 
	 * for (int j = 0; j < nomeUsuarios.size(); j++) {
	 * 
	 * 
	 * 
	 * if(mapSetorCpf.containsKey(nomeUsuarios.get(j).toLowerCase())) {
	 * 
	 * if(usuariosNaoAdicionados.contains(nomeUsuarios.get(j).toLowerCase())) {
	 * usuariosNaoAdicionados.remove(nomeUsuarios.get(j).toLowerCase()); }
	 * 
	 * cadastrados.add(nomeUsuarios.get(j).toLowerCase());
	 * 
	 * dao.atualizarLoginUsuario(nomeUsuarios.get(j),
	 * mapSetorCpf.get(nomeUsuarios.get(j).toLowerCase()));
	 * 
	 * }else { if(!(cadastrados.contains(nomeUsuarios.get(j).toLowerCase())))
	 * usuariosNaoAdicionados.add(nomeUsuarios.get(j).toLowerCase()); }
	 * 
	 * 
	 * }
	 * 
	 * }
	 * 
	 * 
	 * criarPlanilhaExcel(usuariosNaoAdicionados); }
	 */

}
