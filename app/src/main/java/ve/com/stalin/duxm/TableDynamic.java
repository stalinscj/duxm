package ve.com.stalin.duxm;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class TableDynamic {

    private TableLayout tableLayout;
    private Context context;

    public TableDynamic(TableLayout tableLayout, Context context) {
        this.tableLayout = tableLayout;
        this.context = context;
        this.llenar();
    }

    private TableRow newRow() {
        TableRow row = new TableRow(this.context);
        row.setGravity(Gravity.CENTER);

        return row;
    }

    private TextView newCell() {
        TextView cell = new TextView(this.context);
        cell.setGravity(Gravity.CENTER);

        return cell;
    }

    private TableRow.LayoutParams newTableRowsParams() {
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.setMargins(1, 1, 1, 1);
        params.weight = 1;

        return params;
    }

    private void llenar(){
        String[] nombres = {"AB123WWW", "Bolívar -\nPuerto Ordaz", "11:30 am\n31-12-2018"};
        TableRow row = newRow();
        row.setBackgroundColor(Color.RED);

        for (String nombre: nombres) {
            TextView cell = newCell();
            cell.setText(nombre);
            cell.setBackgroundColor(Color.BLUE);
            row.addView(cell, newTableRowsParams());

        }

        tableLayout.addView(row);

        TableRow row2 = newRow();
        row2.setBackgroundColor(Color.BLACK);

        for (String nombre: nombres) {
            TextView cell = newCell();
            cell.setText(nombre);
            cell.setBackgroundColor(Color.WHITE);
            row2.addView(cell, newTableRowsParams());

        }

        tableLayout.addView(row2);

    }
}
