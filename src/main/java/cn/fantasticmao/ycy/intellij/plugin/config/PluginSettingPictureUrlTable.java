package cn.fantasticmao.ycy.intellij.plugin.config;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.table.JBTable;
import com.intellij.util.ui.EditableModel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 插件设置页面的表格
 *
 * @author maomao
 * @version 1.3
 * @since 2019-05-05
 */
public class PluginSettingPictureUrlTable extends JBTable {
    private static final Logger LOG = Logger.getInstance(PluginSettingPictureUrlTable.class);

    /**
     * 序号列
     */
    private static final int ORDER_COLUMN = 0;

    /**
     * 图片 URL 列
     */
    private static final int URL_COLUMN = 1;

    public PluginSettingPictureUrlTable(List<String> defaultPictureUrlList) {
        super(new ModelAdapter(defaultPictureUrlList));
        super.setStriped(true);

        final TableColumnModel columnModel = getColumnModel();
        final TableColumn orderColumn = columnModel.getColumn(ORDER_COLUMN);
        orderColumn.setPreferredWidth(20);

        final TableColumn urlColumn = columnModel.getColumn(URL_COLUMN);
        urlColumn.setPreferredWidth(280);
    }

    /**
     * 获取表格的 {@link javax.swing.table.TableModel}
     *
     * <p>实现 {@link com.intellij.util.ui.EditableModel} 表示表格是可编辑的</p>
     *
     * @see com.intellij.ui.ToolbarDecorator#createDecorator(JTable)
     */
    @Override
    public ModelAdapter getModel() {
        return (ModelAdapter) super.getModel();
    }

    /**
     * 重置表格数据
     */
    public void resetTableList() {
        this.getModel().setRowsData(DefaultConfig.REMIND_PICTURE_LIST);
        LOG.info("reset picture url list to default");
    }

    /**
     * 获取表格数据
     */
    public List<String> getTableList() {
        List<String> pictureList = this.getModel().getRowsData();
        LOG.info("get picture url list: " + pictureList);
        return pictureList;
    }

    /**
     * 设置表格数据
     */
    public void setTableList(List<String> pictureList) {
        this.getModel().setRowsData(pictureList);
        LOG.info("set picture url list to: " + pictureList);
    }

    /**
     * 插件设置页面的表格的 {@link javax.swing.table.TableModel}
     *
     * <p>实现 {@link com.intellij.util.ui.EditableModel} 表示表格是可编辑的</p>
     */
    private static class ModelAdapter extends AbstractTableModel implements EditableModel {
        private static final Logger LOG = Logger.getInstance(ModelAdapter.class);

        /**
         * 图片 URL 列表数据
         */
        private final List<String> pictureUrlList;

        public ModelAdapter(List<String> defaultPictureUrlList) {
            // 复制表格数据（使用深拷贝模式），避免修改默认图片配置
            this.pictureUrlList = new ArrayList<>(defaultPictureUrlList.size());
            this.pictureUrlList.addAll(defaultPictureUrlList);
        }

        /**
         * 获取总行数
         */
        @Override
        public int getRowCount() {
            return pictureUrlList.size();
        }

        /**
         * 获取总列数
         */
        @Override
        public int getColumnCount() {
            return 2;
        }

        /**
         * 根据列号，获取默认列名
         */
        @Override
        public String getColumnName(int column) {
            return column == ORDER_COLUMN
                ? I18nBundle.message(I18nBundle.Key.CONFIG_TABLE_COLUMN_0)
                : I18nBundle.message(I18nBundle.Key.CONFIG_TABLE_COLUMN_1);
        }

        /**
         * 设置单元格的 {@link java.lang.Class}
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
            return columnIndex == ORDER_COLUMN ? String.valueOf(rowIndex) : pictureUrlList.get(rowIndex);
        }

        /**
         * 添加表格行
         */
        @Override
        public void addRow() {
            FileChooserDescriptor descriptor = PluginSettingConfig.PICTURE_FILE_CHOOSER_DESCRIPTOR;
            FileChooser.chooseFiles(descriptor, null, null, fileList -> {
                List<String> chosenPictureUrlList = fileList.stream()
                    .map(VirtualFile::getUrl)
                    .filter(pictureUrl -> !pictureUrlList.contains(pictureUrl))
                    .collect(Collectors.toList());
                if (chosenPictureUrlList.size() != 0) {
                    pictureUrlList.addAll(chosenPictureUrlList);
                    LOG.info("add rows: " + chosenPictureUrlList);
                    super.fireTableRowsInserted(pictureUrlList.size() - 1 - chosenPictureUrlList.size(), pictureUrlList.size() - 1);
                } else {
                    LOG.info("choose no files");
                }
            });
        }

        /**
         * 交换表格行
         */
        @Override
        public void exchangeRows(int oldIndex, int newIndex) {
            final String oldPictureUrl = pictureUrlList.get(oldIndex);
            final String newPictureUrl = pictureUrlList.get(newIndex);
            pictureUrlList.set(oldIndex, newPictureUrl);
            pictureUrlList.set(newIndex, oldPictureUrl);
            LOG.info(String.format("exchange rows index: %d -> %d", oldIndex, newIndex));
            super.fireTableRowsUpdated(Math.min(oldIndex, newIndex), Math.max(oldIndex, newIndex));
        }

        /**
         * 是否可以交换表格行
         */
        @Override
        public boolean canExchangeRows(int oldIndex, int newIndex) {
            return true;
        }

        /**
         * 删除表格行
         */
        @Override
        public void removeRow(int idx) {
            pictureUrlList.remove(idx);
            LOG.info("remove row index: " + idx);
            super.fireTableRowsDeleted(idx, idx);
        }

        /**
         * 获取表格行数据
         */
        public List<String> getRowsData() {
            List<String> rows = Collections.unmodifiableList(new ArrayList<>(pictureUrlList));
            LOG.info("get rows data: " + rows);
            return rows;
        }

        /**
         * 设置表格行数据
         */
        public void setRowsData(List<String> list) {
            pictureUrlList.clear();
            pictureUrlList.addAll(list);
            LOG.info("set rows data: " + list);
            super.fireTableDataChanged();
        }
    }
}
