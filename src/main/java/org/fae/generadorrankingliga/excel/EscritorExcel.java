package org.fae.generadorrankingliga.excel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.fae.generadorrankingliga.vista.Principal;

public class EscritorExcel {
	
	public static void writeToExcell(JTable table, String path) throws FileNotFoundException, IOException {
		try {
			TableModel modeloTabla = table.getModel();
			Workbook workBook = new HSSFWorkbook();
		    Sheet sheet = workBook.createSheet(Principal.clasificacionSeleccionada.toString());
		    Row row = null;
		    Cell cell = null;
		    
		    for (int i = 0; i < modeloTabla.getRowCount(); i++) {
		        row = sheet.createRow(i);
		        for (int j = 0; j < modeloTabla.getColumnCount(); j++) {
		            cell = row.createCell(j);
		            cell.setCellValue((String) modeloTabla.getValueAt(i, j).toString());
		        }
		    }
		     
		    FileOutputStream out = new FileOutputStream(path + ".xls");
		    workBook.write(out);
		    out.close();
		    workBook.close();
		    
		} catch (FileNotFoundException ex) {
		} catch (IOException ex) { }
	}

}
