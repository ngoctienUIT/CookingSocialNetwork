package com.example.cookingsocialnetwork.post2.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerRowMoveCallBack extends ItemTouchHelper.Callback {
    private RecyclerViewRowTouchHelperContract touchHelperContract;
    public RecyclerRowMoveCallBack(RecyclerViewRowTouchHelperContract touchHelperContract){
        this.touchHelperContract = touchHelperContract;
    }
    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        return makeMovementFlags(dragFlag,0);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        this.touchHelperContract.onRowMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return false;
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        if(actionState != ItemTouchHelper.ACTION_STATE_IDLE){
            if(viewHolder instanceof IngredientAdapter.ViewHolder){
                IngredientAdapter.ViewHolder ingredientViewHolder = (IngredientAdapter.ViewHolder)viewHolder;
                touchHelperContract.onRowSelected(ingredientViewHolder);
            }
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        if(viewHolder instanceof IngredientAdapter.ViewHolder){
            IngredientAdapter.ViewHolder ingredientViewHolder = (IngredientAdapter.ViewHolder)viewHolder;
            touchHelperContract.onRowClear(ingredientViewHolder);
        }
        super.clearView(recyclerView, viewHolder);
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }
    public interface RecyclerViewRowTouchHelperContract{
        void onRowMove(int from, int to);
        void onRowSelected(IngredientAdapter.ViewHolder ingredientViewHolder);
        void onRowClear(IngredientAdapter.ViewHolder ingredientViewHolder);

    }
}
