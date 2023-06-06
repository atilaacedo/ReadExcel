package excelbanco;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import excelbanco.dao.ExcelDAO;
import excelbanco.model.Responsavel;
import excelbanco.model.Sala;

public class ReadExcel {
	
	private List<Sala> listarSalas;
	ExcelDAO dao = new ExcelDAO();
	public static void main(String[] args) {
		ReadExcel r = new ReadExcel();
		
		r.atualizarSalas();
	}
	
	public List<Sala> getSalas(int cod_local){
		return dao.getSalas(cod_local);		
	}
	
	
	public List<Responsavel> lerExcel(String caminho, int index) {

		List<Responsavel> responsaveis = new ArrayList<Responsavel>();

		try {
			FileInputStream plan = new FileInputStream(new File(caminho));

			XSSFWorkbook workBook = new XSSFWorkbook(plan);

			XSSFSheet sheetUsuarios = workBook.getSheetAt(index);

			Iterator<Row> rowIterator = sheetUsuarios.iterator();

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				Responsavel res = new Responsavel();
				while (cellIterator.hasNext()) {

					Cell cell = cellIterator.next();
					if (cell.getColumnIndex() == 0 && !(cell.getStringCellValue().equalsIgnoreCase("Responsável"))) {
						res.setNome(cell.getStringCellValue());
					}else if (cell.getColumnIndex() == 1 && !(cell.getStringCellValue().equalsIgnoreCase("email"))) {
						res.setEmail(cell.getStringCellValue());
					} if (cell.getColumnIndex() == 2 && !(cell.getStringCellValue().equals("setor"))) {
						res.setSetor(cell.getStringCellValue());
					} 
					System.out.println(res.getSetor() + " "+ res.getNome());
				}
				responsaveis.add(res);
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

		return responsaveis;
	}
	
	public void atualizarSalas() {
		listarSalas = getSalas(30);
		
		String path = "C://Users//Medkey//Downloads/Aracaju.xlsx";
		List<Responsavel> listResponsaveis = lerExcel(path, 1);
		for (int i = 0; i < listarSalas.size(); i++) {
			for (int j = 0; j < listResponsaveis.size(); j++) {
				System.out.println(listarSalas.get(i).getDesSala() + " IGUAL A " + listResponsaveis.get(j).getSetor());
				 if(listarSalas.get(i).getDesSala().equalsIgnoreCase(listResponsaveis.get(j).getSetor())) {
					 
					 dao.atualizarSalas(listarSalas.get(i).getCodSala(), listResponsaveis.get(j).getEmail(), dao.buscarUsuarioPeloNome(listResponsaveis.get(j).getNome()));
					 break;
				 }
			}
		}
	}
}
