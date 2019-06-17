package kr.hs.dgsw.adeyeo.recycler;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import kr.hs.dgsw.adeyeo.R;
import kr.hs.dgsw.adeyeo.domain.ResultValues;


class ItemViewHolder extends RecyclerView.ViewHolder {

    private TextView textKor;
    private TextView textEng;
    private TextView textZip;
    private ClipboardManager clipboardManager;
    ItemViewHolder(@NonNull View itemView) {
        super(itemView);

        textKor = itemView.findViewById(R.id.textKor);
        textEng = itemView.findViewById(R.id.textEng);
        textZip = itemView.findViewById(R.id.textZip);

        textKor.setOnClickListener(v -> {
            clipboardManager = (ClipboardManager) v.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("주소",textKor.getText().toString());
            clipboardManager.setPrimaryClip(clip);
            Toast.makeText(v.getContext(), "주소가 복사되었습니다.", Toast.LENGTH_SHORT).show();
        });
        textEng.setOnClickListener(v -> {
            clipboardManager = (ClipboardManager) v.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("address",textEng.getText().toString());
            clipboardManager.setPrimaryClip(clip);
            Toast.makeText(v.getContext(), "Address가 복사되었습니다.", Toast.LENGTH_SHORT).show();
        });
        textKor.setOnClickListener(v -> {
            clipboardManager = (ClipboardManager) v.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("우편번호",textZip.getText().toString());
            clipboardManager.setPrimaryClip(clip);
            Toast.makeText(v.getContext(), "우편번호가 복사되었습니다.", Toast.LENGTH_SHORT).show();
        });
    }

    void onBind(ResultValues resultValues){

        textKor.setText(resultValues.getKor());
        textEng.setText(resultValues.getEng());
        textZip.setText(resultValues.getZip());

    }

}
