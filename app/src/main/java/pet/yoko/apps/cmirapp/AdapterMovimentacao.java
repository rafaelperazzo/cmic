package pet.yoko.apps.cmirapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pet.yoko.apps.cmirapp.db.Movimentacao;
import pet.yoko.apps.cmirapp.tasks.TaskDeletarMovimentacao;

public class AdapterMovimentacao extends RecyclerView.Adapter <AdapterMovimentacao.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView item_id;
        public TextView item_descricao;
        public TextView item_quantidade;
        public TextView item_finalidade;
        public TextView item_data;
        public ImageView item_remover;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_id = (TextView)itemView.findViewById(R.id.txtTabId);
            item_descricao = (TextView)itemView.findViewById(R.id.txtTabItem);
            item_quantidade = (TextView)itemView.findViewById(R.id.txtTabQuantidade);
            item_finalidade = (TextView)itemView.findViewById(R.id.txtTabFinalidade);
            item_data = (TextView)itemView.findViewById(R.id.txtTabData);
            item_remover = (ImageView)itemView.findViewById(R.id.imgTabRemover);
        }
    }

    private List<Movimentacao> items;
    private Context context;

    public List<Movimentacao> getItems() {
        return items;
    }

    public void setItems(List<Movimentacao> items) {
        this.items = items;
    }

    public AdapterMovimentacao(List<Movimentacao> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterMovimentacao.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.tab_movimentacao,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMovimentacao.ViewHolder holder, final int position) {
        Movimentacao movimentacao = items.get(position);
        TextView item_id = holder.item_id;
        TextView item_descricao = holder.item_descricao;
        TextView item_quantidade = holder.item_quantidade;
        TextView item_finalidade = holder.item_finalidade;
        TextView item_data = holder.item_data;
        ImageView item_remover = holder.item_remover;
        item_id.setText(String.valueOf(movimentacao.getId()));
        item_descricao.setText(movimentacao.getItem());
        item_quantidade.setText(String.valueOf(movimentacao.getQuantidade()));
        item_finalidade.setText(movimentacao.getFinalidade());
        item_data.setText(movimentacao.getData());
        item_remover.setOnClickListener(view -> removerLinha(position));
    }

    public void removerLinha(int position) {
        /*int id = items.get(position).getId();
        TaskDeletarMovimentacao removerMovimentacao = new TaskDeletarMovimentacao(id);
        removerMovimentacao.execute();
        items.remove(position);
        notifyDataSetChanged();*/
        confirmacao(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void confirmacao(int position) {
        new AlertDialog.Builder(this.context)
                .setTitle("Confirmação")
                .setMessage("Tem certeza ? A operação não poderá ser desfeita!")
                //.setIcon(android.R.drawable.ic_dialog_alert)
                .setIcon(context.getDrawable(R.drawable.alert))
                .setPositiveButton("SIM", (dialog, whichButton) -> {
                    int id = items.get(position).getId();
                    TaskDeletarMovimentacao removerMovimentacao = new TaskDeletarMovimentacao(id);
                    removerMovimentacao.execute();
                    items.remove(position);
                    notifyDataSetChanged();
                })
                .setNegativeButton("CANCELAR", null).show();
    }
}
