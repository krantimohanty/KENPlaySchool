package com.kenplayschool.fragment;


import android.app.Fragment;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kenplayschool.R;
import com.telerik.android.common.Util;
import com.telerik.widget.list.ItemReorderBehavior;
import com.telerik.widget.list.ListViewAdapter;
import com.telerik.widget.list.ListViewHolder;
import com.telerik.widget.list.RadListView;
import com.telerik.widget.list.SwipeExecuteBehavior;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListViewReorderFragment extends ExampleFragmentBase {

    private RadListView listView;
    private ItemsAdapter todoAdapter;
    private ItemsAdapter shoppingAdapter;

    private View reorderView;
    private View menuSelection;

    private float buttonMinWidth;

    public ListViewReorderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_list_view_reorder, container, false);

        this.listView = (RadListView) rootView.findViewById(R.id.listView);

        ItemReorderBehavior reorderBehavior = new ItemReorderBehavior();
        reorderBehavior.addListener(new ReorderListener());
        this.listView.addBehavior(reorderBehavior);

        SwipeExecuteBehavior swipeExecuteBehavior = new SwipeExecuteBehavior();
        swipeExecuteBehavior.addListener(new SwipeListener(swipeExecuteBehavior));
        swipeExecuteBehavior.setAutoDissolve(false);
        this.listView.addBehavior(swipeExecuteBehavior);

        this.initExampleStyles(rootView);
        this.initAdapters(savedInstanceState);

        boolean isTodoSelected = true;
        if (savedInstanceState != null) {
            isTodoSelected = savedInstanceState.getBoolean("isTodoSelected");
        }

        this.initMenu(rootView, isTodoSelected);
        if (isTodoSelected) {
            this.listView.setAdapter(this.todoAdapter);
        } else {
            this.listView.setAdapter(this.shoppingAdapter);
        }

        return rootView;
    }

    public void complete(int position) {
        ListViewAdapter adapter = (ListViewAdapter) this.listView.getAdapter();
        TODOItem item = (TODOItem) adapter.getItem(position);
        item.isCompleted = !item.isCompleted;
        adapter.notifyItemChanged(position);
    }

    public void delete(int position) {
        ListViewAdapter adapter = (ListViewAdapter) this.listView.getAdapter();
        adapter.remove(position);
    }

    class SwipeListener implements SwipeExecuteBehavior.SwipeExecuteListener {
        private SwipeExecuteBehavior behavior;
        private int colorComplete;
        private int colorDelete;

        public SwipeListener(SwipeExecuteBehavior behavior) {
            this.behavior = behavior;
            this.colorComplete = getResources().getColor(R.color.list_view_example_reorder_complete_background);
            this.colorDelete = getResources().getColor(R.color.list_view_example_reorder_delete_background);
        }

        @Override
        public void onSwipeStarted(int position) {
        }

        @Override
        public void onSwipeProgressChanged(int position, int currentOffset, View swipeContent) {
            View leftLayout = ((ViewGroup) swipeContent).getChildAt(0);
            View rightLayout = ((ViewGroup) swipeContent).getChildAt(1);

            if (currentOffset > 0) {
                int width = currentOffset > buttonMinWidth ? currentOffset : (int) buttonMinWidth;
                ViewGroup.LayoutParams leftParams = leftLayout.getLayoutParams();
                leftParams.width = width;
                leftLayout.setLayoutParams(leftParams);

                leftLayout.setVisibility(View.VISIBLE);
                rightLayout.setVisibility(View.INVISIBLE);

                swipeContent.setBackgroundColor(colorComplete);
            } else {
                int width = currentOffset < -buttonMinWidth ? -currentOffset : (int) buttonMinWidth;
                ViewGroup.LayoutParams rightParams = rightLayout.getLayoutParams();
                rightParams.width = width;
                rightLayout.setLayoutParams(rightParams);

                leftLayout.setVisibility(View.INVISIBLE);
                rightLayout.setVisibility(View.VISIBLE);

                swipeContent.setBackgroundColor(colorDelete);
            }
        }

        @Override
        public void onSwipeEnded(int position, int finalOffset) {
            if (-buttonMinWidth < finalOffset && finalOffset < buttonMinWidth) {
                behavior.setSwipeOffset(0);
                return;
            }
            if (finalOffset > 0) {
                complete(position);
            } else {
                delete(position);
            }
            behavior.endExecute();
        }

        @Override
        public void onExecuteFinished(int position) {
        }
    }

    class ReorderListener implements ItemReorderBehavior.ItemReorderListener {
        @Override
        public void onReorderStarted(int position) {
            int visibleViewsCount = listView.getLayoutManager().getChildCount();
            for (int i = 0; i < visibleViewsCount; i++) {
                View child = listView.getLayoutManager().getChildAt(i);
                int pos = listView.getChildAdapterPosition(child);
                if (pos == position) {
                    reorderView = child;
                    reorderView.setActivated(true);
                    return;
                }
            }
        }

        @Override
        public void onReorderItem(int positionFrom, int positionTo) {
        }

        @Override
        public void onReorderFinished() {
            if (reorderView != null) {
                reorderView.setActivated(false);
                reorderView = null;
            }
        }
    }

    class ItemsAdapter extends ListViewAdapter {
        int layoutResource;

        public ItemsAdapter(List<TODOItem> items, int layout) {
            super(items);
            this.layoutResource = layout;
        }

        @Override
        public TODOItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(this.layoutResource, parent, false);
            return new TODOItemViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ListViewHolder vh, int i) {
            TODOItem item = (TODOItem) this.getItem(i);

            TODOItemViewHolder typedVh = (TODOItemViewHolder) vh;
            typedVh.itemTitle.setText(item.title);
            if (item.isCompleted) {
                typedVh.itemTitle.setPaintFlags(typedVh.itemTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                typedVh.itemTitle.setPaintFlags(typedVh.itemTitle.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }

        @Override
        public ListViewHolder onCreateSwipeContentHolder(ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.list_view_reorder_swipe_content, parent, false);
            return new ListViewHolder(itemView);
        }
    }

    class TODOItem implements Parcelable {
        public String title;
        public boolean isCompleted;

        public TODOItem(String title) {
            this(title, false);
        }

        public TODOItem(String title, boolean isCompleted) {
            this.title = title;
            this.isCompleted = isCompleted;
        }

        public TODOItem(Parcel source) {
            title = source.readString();
            isCompleted = source.readInt() != 0;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(title);
            dest.writeInt(isCompleted ? 1 : 0);
        }

        public final Creator<TODOItem> CREATOR = new Creator<TODOItem>() {
            @Override
            public TODOItem[] newArray(int size) {
                return new TODOItem[size];
            }

            @Override
            public TODOItem createFromParcel(Parcel source) {
                return new TODOItem(source);
            }
        };
    }

    class TODOItemViewHolder extends ListViewHolder {

        public TextView itemTitle;
        public ImageView itemImage;

        public TODOItemViewHolder(View itemView) {
            super(itemView);

            this.itemImage = (ImageView) itemView.findViewById(R.id.itemImage);
            this.itemTitle = (TextView) itemView.findViewById(R.id.itemTitle);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (listView == null || todoAdapter == null || shoppingAdapter == null) {
            return;
        }

        ArrayList<TODOItem> todoItems = (ArrayList<TODOItem>) todoAdapter.getItems();
        outState.putParcelableArrayList("todoItems", todoItems);

        ArrayList<TODOItem> shoppingItems = (ArrayList<TODOItem>) shoppingAdapter.getItems();
        outState.putParcelableArrayList("shoppingItems", shoppingItems);

        outState.putBoolean("isTodoSelected", listView.getAdapter() == todoAdapter);
    }

    private void switchAdapter() {
        ListViewAdapter currentAdapter = (ListViewAdapter) listView.getAdapter();
        if (currentAdapter == todoAdapter) {
            listView.setAdapter(shoppingAdapter);
        } else {
            listView.setAdapter(todoAdapter);
        }
    }

    private void initExampleStyles(View rootView) {

        LayoutInflater inflater = LayoutInflater.from(rootView.getContext());
        View emptyView = inflater.inflate(R.layout.list_view_reorder_empty, this.listView, false);
        this.listView.setEmptyContent(emptyView);

        buttonMinWidth = Util.getDimen(TypedValue.COMPLEX_UNIT_DIP, 70);

        Typeface robotoSlab = Typeface.createFromAsset(this.getActivity().getAssets(), "fonts/RobotoSlabRegular.ttf");
        Typeface robotoSlabBold = Typeface.createFromAsset(this.getActivity().getAssets(), "fonts/RobotoSlab700.ttf");

        TextView header = (TextView) rootView.findViewById(R.id.header);
        header.setTypeface(robotoSlabBold);

        TextView toDoListText = (TextView) rootView.findViewById(R.id.toDoListText);
        toDoListText.setTypeface(robotoSlab);

        TextView shoppingListText = (TextView) rootView.findViewById(R.id.shoppingListText);
        shoppingListText.setTypeface(robotoSlab);
    }

    private void initMenu(View rootView, boolean isToDoSelected) {
        final MenuItemOnClickListener listener = new MenuItemOnClickListener();

        View menuItemToDo = rootView.findViewById(R.id.menuItemToDo);
        menuItemToDo.setOnClickListener(listener);

        View menuItemShopping = rootView.findViewById(R.id.menuItemShopping);
        menuItemShopping.setOnClickListener(listener);

        if (isToDoSelected) {
            this.menuSelection = menuItemToDo;
        } else {
            this.menuSelection = menuItemShopping;
        }
        this.menuSelection.setActivated(true);
    }

    private void initAdapters(Bundle savedInstanceState) {
        List<TODOItem> todoList = null;
        List<TODOItem> shoppingList = null;

        if (savedInstanceState != null) {
            ArrayList todoItems = savedInstanceState.getParcelableArrayList("Facilities");
            if (todoItems != null) {
                todoList = (List<TODOItem>) todoItems;
            }
            ArrayList shoppingItems = savedInstanceState.getParcelableArrayList("Specialties");
            if (shoppingItems != null) {
                shoppingList = (List<TODOItem>) shoppingItems;
            }
        }

        if (todoList == null) {
            todoList = createToDoList();
        }
        if (shoppingList == null) {
            shoppingList = createShoppingList();
        }

        this.todoAdapter = new ItemsAdapter(todoList, R.layout.list_view_reorder_todo_layout);
        this.shoppingAdapter = new ItemsAdapter(shoppingList, R.layout.list_view_reorder_shopping_layout);
    }

    class MenuItemOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v == menuSelection) {
                return;
            }
            menuSelection.setActivated(false);
            menuSelection = v;
            menuSelection.setActivated(true);
            switchAdapter();
        }
    }

    private List<TODOItem> createToDoList() {

        ArrayList<TODOItem> todoList = new ArrayList<TODOItem>();
        TODOItem todoItem = new TODOItem("Full Time Day Care Facilities");
        todoList.add(todoItem);

        todoItem = new TODOItem("Babysitting For Brief Period");
        todoList.add(todoItem);

        todoItem = new TODOItem("CCTV in Each Room");
        todoList.add(todoItem);

        todoItem = new TODOItem("Air-Conditioned Rooms");
        todoList.add(todoItem);

        todoItem = new TODOItem("Doctor on Call", true);
        todoList.add(todoItem);

        todoItem = new TODOItem("Yoga Sessions");
        todoList.add(todoItem);

        todoItem = new TODOItem("24X7 Creche With Video Calling", true);
        todoList.add(todoItem);

        return todoList;
    }

    private List<TODOItem> createShoppingList() {

        ArrayList<TODOItem> shoppingList = new ArrayList<TODOItem>();
        TODOItem todoItem = new TODOItem("24x7, full day service for parents with irregular work schedules");
        shoppingList.add(todoItem);

        todoItem = new TODOItem("Specially designed mobile app for parents to update themselves");
        shoppingList.add(todoItem);

        todoItem = new TODOItem("Experts to serve mothers with maternity leave");
        shoppingList.add(todoItem);

        todoItem = new TODOItem("Any time availability");
        shoppingList.add(todoItem);

        todoItem = new TODOItem("Certified, experienced and professional care");
        shoppingList.add(todoItem);

        todoItem = new TODOItem("Exceptional surveillance and regular contact with parents");
        shoppingList.add(todoItem);

        todoItem = new TODOItem("Individual facilities like sleeping area, playing area, food courts, etc");
        shoppingList.add(todoItem);

        todoItem = new TODOItem("Separate routines for every child", true);
        shoppingList.add(todoItem);

//        todoItem = new TODOItem("Carrots", true);
//        shoppingList.add(todoItem);
//
//        todoItem = new TODOItem("Apples");
//        shoppingList.add(todoItem);
//
//        todoItem = new TODOItem("Tomatoes");
//        shoppingList.add(todoItem);
        return shoppingList;
    }
}
