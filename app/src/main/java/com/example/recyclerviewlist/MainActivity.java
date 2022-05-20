package com.example.recyclerviewlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView recycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycle=findViewById(R.id.recycle);

        listingdata();
        LinearLayoutManager llm=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recycle.setLayoutManager(llm);
    }

    private void listingdata() {
        ApiInterface apiInterface=Retrofit.getRetrofit().create(ApiInterface.class);
        Call<Pojo> listingdata=apiInterface.getData();
        listingdata.enqueue(new Callback<Pojo>() {
            @Override
            public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                if(response.isSuccessful())
                {
                    recycleradapter adapter=new recycleradapter(response.body().getData());
                    recycle.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failure",Toast.LENGTH_SHORT).show();

            }
        });
    }

    class recycleradapter extends RecyclerView.Adapter<recycleradapter.MyViewHolder>
    {
        List<Pojo.Datum> list;

        public recycleradapter(List<Pojo.Datum> list){
            this.list=list;
        }

        @NonNull

        @Override
        public recycleradapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardlayout,null);
            recycleradapter.MyViewHolder viewHolder= new recycleradapter.MyViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull  recycleradapter.MyViewHolder holder, int position) {

            holder.email.setText(list.get(position).getEmail());
            holder.fstname.setText(list.get(position).getFirstName());
            holder.lstname.setText(list.get(position).getLastName());
            Picasso.with(getApplicationContext()).load(list.get(position).getAvatar()).placeholder(R.drawable.ic_launcher_background).fit().into(holder.imgs);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView fstname,userid,lstname,email;
            ImageView imgs;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                email=itemView.findViewById(R.id.email);
                fstname=itemView.findViewById(R.id.fstname);
                lstname=itemView.findViewById(R.id.lstname);
              //  userid=itemView.findViewById(R.id.userid);
                imgs=itemView.findViewById(R.id.imgs);
            }
        }
    }
}