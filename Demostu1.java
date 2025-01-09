package swingapp.package1;
import swingapp.db.*;
import swingapp.util.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;

import javax.swing.*;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class Demostu extends JFrame {
	private DefaultTableModel tableModel;
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField text;//隐藏ID
	private JTextField textx;//隐藏ID
	private JTextField atext;//学号
	private JTextField btext;//姓名
	private JTextField ctext;//班级号
	private JTextField dtext;//年龄
	private JTextField etext;//性别
	private JTextField ftext;//身高
	private JTextField gtext;//体重
	private JButton addbtn, delbtn, updbtn;
	public static boolean dellast=false;//删除最后1条标志
	private String filename = "config_stu.ini";
	String[][] tableValues = null;
	
	public Demostu() {
		
		setTitle("Student_Table");
		setBounds(150,300,1420,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container c = getContentPane();
		DBMqlCon con = new DBMqlCon();
		con.start();
		
		String column = util.getProps(filename).getProperty("column");
		String selectsql = util.getProps(filename).getProperty("selectsql");
		String countsql = util.getProps(filename).getProperty("countsql");
		
//		System.out.println(column);
		
		String[] columnNames = this.getHead(column);
		tableValues = this.getData(tableValues, countsql,selectsql,column);
		System.out.println(tableValues.length);
		tableModel = new DefaultTableModel(tableValues, columnNames);
		
		table = new JTable(tableModel);
		table.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
		table.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
		table.getTableHeader().getColumnModel().getColumn(0).setPreferredWidth(0);
		table.setRowHeight(30);
		

		table = new JTable(tableModel);//锟斤拷锟斤拷指锟斤拷锟斤拷锟侥ｏ拷偷谋锟斤拷
		JScrollPane JS = new JScrollPane(table);//锟斤拷踊锟斤拷锟斤拷锟斤拷
		c.add(JS, BorderLayout.CENTER);//锟斤拷锟斤拷锟斤拷锟斤拷锟叫诧拷锟斤拷锟斤拷位锟斤拷
		JPanel pane1 = new JPanel();//锟斤拷锟�
		c.add(pane1,BorderLayout.SOUTH);//锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟�
		
		//pane1.add(new JLabel("id:"));//锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟接憋拷签
		text = new JTextField(10);
		textx= new JTextField(10);
		textx.setVisible(false);
		text.setVisible(false);
		pane1.add(text);
		
		
	   /**
	    * 以下是需要显示的字段
	    */
		pane1.add(new JLabel("stu_id:"));
		atext = new JTextField(10);
		pane1.add(atext);
		
		pane1.add(new JLabel("name:"));
		btext = new JTextField(10);
		pane1.add(btext);
		
		
		pane1.add(new JLabel("class_id:"));
		ctext = new JTextField(10);
		pane1.add(ctext);
		
		pane1.add(new JLabel("age:"));
		dtext = new JTextField(10);
		pane1.add(dtext);
		
		pane1.add(new JLabel("sex:"));
		etext = new JTextField(10);
		pane1.add(etext);
		
		pane1.add(new JLabel("height:"));
		ftext = new JTextField(10);
		pane1.add(ftext);
		
		pane1.add(new JLabel("weight:"));
		gtext = new JTextField(10);
		pane1.add(gtext);
		
		
		addbtn=new JButton("add");
		updbtn=new JButton("update");
		delbtn=new JButton("delete");
		pane1.add(addbtn);
		pane1.add(updbtn);
		pane1.add(delbtn);
		
		addbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rows = table.getRowCount();
				if(dellast){
					 
					text.setText(String.valueOf(Integer.parseInt(textx.getText())+1));
				}
				else{
				if(rows>0){
				  Object value = table.getValueAt(rows-1, 0);
				  text.setText(String.valueOf(Integer.parseInt(value.toString())+1));
				}
				else{
					text.setText("1");
				}
				}
				String[] rowValues = {text.getText(),atext.getText(),btext.getText(),ctext.getText(),dtext.getText(),etext.getText(),ftext.getText(),gtext.getText()};//锟斤拷锟斤拷谋锟斤拷锟斤拷锟斤拷锟斤拷
				String[] data={atext.getText(),btext.getText(),ctext.getText(),dtext.getText(),etext.getText(),ftext.getText(),gtext.getText()};//锟斤拷锟斤拷谋锟斤拷锟斤拷锟斤拷锟斤拷
				boolean flag=saveData(data);
				if(flag){
					
				 tableModel.addRow(rowValues);//锟斤拷锟侥ｏ拷锟斤拷锟斤拷一锟斤拷锟斤拷锟斤拷
				 //refreshTable(table,tableModel,selectRow);
				 //refreshTable(table,tableModel,selectRow);
				}
			}
		});
		//锟斤拷锟矫碉拷锟斤拷薷陌锟脚ワ拷锟斤拷录锟�
		updbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectRow = table.getSelectedRow();//选锟斤拷锟叫碉拷锟斤拷锟斤拷
				if(selectRow!=-1) {//锟斤拷锟节憋拷选锟叫碉拷锟斤拷
					int flag=JOptionPane.showConfirmDialog(null, "锟角凤拷锟睫革拷锟斤拷锟斤拷");
					System.out.println(flag);
					if(flag==0){
						//锟睫革拷
						Object value = table.getValueAt(selectRow, 0);
		                //String nid=value.toString();
		                String nid=text.getText();
		                System.out.println("nid="+nid);
						String[] date={atext.getText(),btext.getText(),ctext.getText(),dtext.getText(),etext.getText(),ftext.getText(),gtext.getText()};//锟斤拷锟斤拷谋锟斤拷锟斤拷锟斤拷锟斤拷
						updateData(date,nid);
						String[] rowValues = {text.getText(),atext.getText(),btext.getText(),ctext.getText(),dtext.getText(),etext.getText(),ftext.getText(),gtext.getText()};//锟斤拷锟斤拷谋锟斤拷锟斤拷锟斤拷锟斤拷
						JOptionPane.showMessageDialog(null, "锟睫改成癸拷");
						refreshTable(table,tableModel,selectRow);
				}
				}
			}
		});
		//锟斤拷锟矫碉拷锟缴撅拷锟斤拷锟脚ワ拷锟斤拷录锟�
		delbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectionRow = table.getSelectedRow();//选锟斤拷锟叫碉拷锟斤拷锟斤拷
				if(selectionRow!=-1) {
					int flag=JOptionPane.showConfirmDialog(null, "锟角凤拷删锟斤拷");
					if(flag==0){
						int row = table.getSelectedRow();
		                int col = table.getSelectedColumn();
					    
					    Object value = table.getValueAt(row, 0);
					    String nid=value.toString();
					    delData(nid);
					    JOptionPane.showMessageDialog(null, "删锟斤拷锟缴癸拷");
					    int rows = table.getRowCount();
					    if(selectionRow==rows-1){
					    	dellast=true;
					    	textx.setText(nid);
					    }else{
					    	dellast=false;
					    }
					    tableModel.removeRow(selectionRow);
					    
					}
				}else{
					JOptionPane.showMessageDialog(null, "未选锟斤拷锟斤拷");
				}
			}
		});
		   // 锟斤拷锟斤拷锟斤拷锟斤拷锟铰硷拷锟斤拷锟斤拷锟斤拷
	    table.addMouseListener(new MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	            // 锟斤拷取锟矫伙拷锟斤拷锟斤拷锟斤拷泻锟斤拷锟斤拷锟斤拷锟�
	            int row = table.getSelectedRow();
	            int col = table.getSelectedColumn();
	            // 锟斤拷取锟斤拷锟斤拷锟皆拷锟斤拷锟斤拷锟斤拷
	            for(int i=0;i<8;i++){
	            Object value = table.getValueAt(row, i);
	            String text1=value.toString();
	            if(i==0){text.setText(text1);}
	            if(i==1){atext.setText(text1);}
	            if(i==2){btext.setText(text1);}
	            if(i==3){ctext.setText(text1);}
	            if(i==4){dtext.setText(text1);}
	            if(i==5){etext.setText(text1);}
	            if(i==6){ftext.setText(text1);}
	            if(i==7){gtext.setText(text1);}
	 
	            // 锟斤拷锟斤拷锟斤拷械锟斤拷锟斤拷锟�
	            //System.out.println("Row " + row + ", Column " + i + ": " + value);
	            }
	        
	        }
	    });
		
//		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
//		setContentPane(contentPane);
	}
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Demostu frame = new Demostu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public String[] getHead(String column) {
		String[] columnNames = null;
		String[] spl = column.split(",");
		if(spl.length == 0) {
			spl = new String[1];
		}
		columnNames = new String[spl.length];
		for(int i = 0; i < spl.length; ++i) {
			columnNames[i] = spl[i];
		}
		return columnNames;
	}
	
	public String[][] getData(String[][] tableValues, String countsql, String selectSql, String column) {
		DBMqlCon con = new DBMqlCon();
		int count = con.exeCountSql(countsql);
		
		String[] spl = column.split(",");
		if(spl.length == 0) {
			spl = new String[1];
		}
		ArrayList li = (ArrayList)con.exeSql(selectSql, column);
		tableValues = new String[count][spl.length];
		for(int i = 0; i < li.size(); ++i) {
			HashMap <String, String> mp = (HashMap)li.get(i);
			System.out.println(mp.toString());
			int j = 0;
			for(String val:mp.values()) {
				tableValues[i][j] = val;
				++j;
			}
		}
		
		return tableValues;
	}
	public boolean  saveData(String[] data){
		DBMqlCon dbcon=new DBMqlCon();
		dbcon.start();
		dbcon.getConnection();
		try {
			 
			//锟斤拷锟斤拷锟斤拷史锟斤拷
			java.text.SimpleDateFormat sf=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sql="insert into student values (null,?,?,?,?,?,?,?)";//锟斤拷锟斤拷锟捷匡拷锟街讹拷匹锟斤拷
			System.out.println(sql);
			PreparedStatement pstmt=dbcon.con.prepareStatement(sql);
			pstmt.setString(1, data[0]);
			pstmt.setString(2, data[1]);
			pstmt.setString(3, data[2]);	
			pstmt.setString(4, data[3]);
			pstmt.setString(5, data[4]);
			pstmt.setString(6, data[5]);
			pstmt.setString(7, data[6]);
		    pstmt.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return true;
	}
public boolean  updateData(String[] data,String uid){
	
	DBMqlCon dbcon=new DBMqlCon();
	dbcon.start();
	dbcon.getConnection();
	try {
		 
		String sql="update student set student_id=?,name=?,class_id=?,age=?,sex=?,height=?,weight=? where id=?";//锟斤拷锟斤拷锟捷匡拷锟街讹拷匹锟斤拷
		PreparedStatement pstmt=dbcon.con.prepareStatement(sql);
		pstmt.setString(1, data[0]);
		pstmt.setString(2, data[1]);
		pstmt.setString(3, data[2]);
		pstmt.setString(4, data[3]);
		pstmt.setString(5, data[4]);
		pstmt.setString(6, data[5]);
		pstmt.setString(7, data[6]);
		pstmt.setString(8, uid);
		  
	    pstmt.executeUpdate();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	return true;
}
public boolean  delData(String nid){
	
	DBMqlCon dbcon=new DBMqlCon();
	dbcon.start();
	dbcon.getConnection();
	try {
 
		String sql="delete from student where id=?";//锟斤拷锟斤拷锟捷匡拷锟街讹拷匹锟斤拷
		PreparedStatement pstmt=dbcon.con.prepareStatement(sql);
		pstmt.setString(1, nid); 
	    pstmt.execute();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	return true;
}

public void refreshTable(JTable table, DefaultTableModel model,int selectRow) {
	tableModel.setValueAt(atext.getText(), selectRow, 1);
	tableModel.setValueAt(btext.getText(), selectRow, 2);
	tableModel.setValueAt(ctext.getText(), selectRow, 3);  	
	
}
	/**
	 * Create the frame.
	 */
	

}
