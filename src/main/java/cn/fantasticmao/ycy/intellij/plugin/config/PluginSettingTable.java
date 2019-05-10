package cn.fantasticmao.ycy.intellij.plugin.config;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDialog;
import com.intellij.openapi.fileChooser.FileChooserFactory;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.table.JBTable;
import com.intellij.util.ui.EditableModel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * PluginSettingTable
 *
 * @author maomao
 * @since 2019-05-05
 */
public class PluginSettingTable extends JBTable {
    private static final Logger LOG = Logger.getInstance(PluginSettingTable.class);
    private static final int ORDER_COLUMN = 0;
    private static final int URL_COLUMN = 1;

    public PluginSettingTable(List<String> defaultImageUrlList) {
        super(new ModelAdapter(defaultImageUrlList));
        super.setStriped(true);

        final TableColumnModel columnModel = getColumnModel();
        final TableColumn orderColumn = columnModel.getColumn(ORDER_COLUMN);
        orderColumn.setPreferredWidth(20);

        final TableColumn urlColumn = columnModel.getColumn(URL_COLUMN);
        urlColumn.setPreferredWidth(280);
    }

    /**
     * 是否可以编辑表格
     *
     * @see com.intellij.ui.ToolbarDecorator#createDecorator(JTable)
     */
    @Override
    public ModelAdapter getModel() {
        return (ModelAdapter) super.getModel();
    }

    /**
     * 重置表格内容
     */
    public void resetToDefault() {
        getModel().resetToDefault();
        LOG.info("reset image url list to default");
    }

    private static class ModelAdapter extends AbstractTableModel implements EditableModel {
        private static final Logger LOG = Logger.getInstance(ModelAdapter.class);
        private final List<String> imageUrlList;

        public ModelAdapter(List<String> defaultImageUrlList) {
            // 使用深拷贝复制对象，避免修改默认图片配置
            this.imageUrlList = new ArrayList<>(defaultImageUrlList.size());
            this.imageUrlList.addAll(defaultImageUrlList);
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
         * 设置单元格对象的 {@link java.lang.Class}
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

        /**
         * 添加表格行
         */
        @Override
        public void addRow() {
            FileChooserDescriptor descriptor = PluginSettingConfig.IMAGE_FILE_CHOOSER;
            FileChooserDialog dialog = FileChooserFactory.getInstance().createFileChooser(descriptor, null, null);
            VirtualFile[] files = dialog.choose(null);
            List<String> chosenImageUrlList = Stream.of(files)
                    .map(imageFile -> {
                        try {
                            return VfsUtil.toUri(imageFile).toURL().toString();
                        } catch (MalformedURLException e) {
                            // ignore
                            e.printStackTrace();
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .filter(imageUrl -> !imageUrlList.contains(imageUrl))
                    .collect(Collectors.toList());
            imageUrlList.addAll(chosenImageUrlList);
            LOG.info("add row :" + chosenImageUrlList);
        }

        /**
         * 交换表格行
         */
        @Override
        public void exchangeRows(int oldIndex, int newIndex) {
            final String oldImgUrl = imageUrlList.get(oldIndex);
            final String newImgUrl = imageUrlList.get(newIndex);
            imageUrlList.set(oldIndex, newImgUrl);
            imageUrlList.set(newIndex, oldImgUrl);
            LOG.info(String.format("exchange rows: %d %d", oldIndex, newIndex));
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
            imageUrlList.remove(idx);
            LOG.info("remove row: " + idx);
        }

        /**
         * 重置表格内容
         */
        public void resetToDefault() {
            imageUrlList.clear();
            imageUrlList.addAll(DefaultConfig.REMIND_IMAGE_LIST);
            LOG.info("reset to default");
        }
    }
}
