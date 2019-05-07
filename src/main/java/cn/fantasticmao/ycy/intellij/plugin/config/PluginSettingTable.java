package cn.fantasticmao.ycy.intellij.plugin.config;

import com.intellij.ui.table.JBTable;
import com.intellij.util.ui.EditableModel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.util.List;

/**
 * PluginSettingTable
 *
 * @author maomao
 * @since 2019-05-05
 */
public class PluginSettingTable extends JBTable {
    private static final int ORDER_COLUMN = 0;
    private static final int URL_COLUMN = 1;

    public PluginSettingTable(List<String> imageUrlList) {
        super(new ModelAdapter(imageUrlList));
        super.setStriped(true);

        final TableColumnModel columnModel = getColumnModel();
        final TableColumn orderColumn = columnModel.getColumn(ORDER_COLUMN);
        orderColumn.setPreferredWidth(20);

        final TableColumn urlColumn = columnModel.getColumn(URL_COLUMN);
        urlColumn.setPreferredWidth(280);
    }

    /**
     * 标识为可编辑的表格
     *
     * @see com.intellij.ui.ToolbarDecorator#createDecorator(JTable)
     */
    @Override
    public ModelAdapter getModel() {
        return (ModelAdapter) super.getModel();
    }

    public void resetToDefault() {
        getModel().resetToDefault();
    }

    private static class ModelAdapter extends AbstractTableModel implements EditableModel {
        private final List<String> imageUrlList;

        public ModelAdapter(List<String> imageUrlList) {
            this.imageUrlList = imageUrlList;
        }

        @Override
        public int getRowCount() {
            return imageUrlList.size();
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        /**
         * 根据列号，获取默认列名
         */
        @Override
        public String getColumnName(int column) {
            return column == ORDER_COLUMN ? "序号" : "图片 URL";
        }

        /**
         * 设置单元格对象 Class
         */
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return String.class;
        }

        /**
         * 是否可以编辑单元格内容
         */
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        /**
         * 根据单元格坐标，获取单元格内容
         */
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return columnIndex == ORDER_COLUMN ? String.valueOf(rowIndex) : imageUrlList.get(rowIndex);
        }

        @Override
        public void addRow() {
            // TODO 添加记录
            System.out.println("TODO 添加记录");
        }

        /**
         * 交换表格行的具体实现
         */
        @Override
        public void exchangeRows(int oldIndex, int newIndex) {
            final String oldImgUrl = imageUrlList.get(oldIndex);
            final String newImgUrl = imageUrlList.get(newIndex);
            imageUrlList.set(oldIndex, newImgUrl);
            imageUrlList.set(newIndex, oldImgUrl);
        }

        /**
         * 标识表格的行可以互相交换
         */
        @Override
        public boolean canExchangeRows(int oldIndex, int newIndex) {
            return true;
        }

        /**
         * 删除表格的行
         */
        @Override
        public void removeRow(int idx) {
            imageUrlList.remove(idx);
        }

        public void resetToDefault() {
            // TODO 使用默认图片配置
            System.out.println("TODO 使用默认图片配置");
        }
    }
}
