package com.example.fv.judgement.app.application;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by lh on 2020/2/18.
 */

public class GlobalMethodApplication  {
    private static int INTEGER_COUNT = 2;
    private static int DECIMAL_COUNT = 1;
    public static void TxtNumber(final EditText mEditText)
    {
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (start == 0 && s.toString().equals(".") && count == 1) {
                    //输入的第一个字符为"."
                    mEditText.setText("");
                } else if (s.length() >= INTEGER_COUNT + 1 && count != 0) {
                    //当整数位数输入到达被要求的上限,并且当前在输入字符,而不是减少字符
                    if (s.toString().contains(".")) {
                        //当前输入的有"."字符
                        String[] text = s.toString().split("\\.");
                        if (text.length >= 2 && text[1].length() > DECIMAL_COUNT) {
                            //小数位数超数
//                          s.delete(text[0].length() + text[1].length() + 1, s.toString().length());
//                          mEditText.setText(s);
                            mEditText.setText(s.subSequence(0, s.toString().length() - 1));
                            mEditText.setSelection(s.toString().length() - 1);
                        }
                    } else {
//                      s.delete(s.toString().length() - 1, s.toString().length());
//                      mEditText.setText(s);
                        mEditText.setText(s.subSequence(0, s.toString().length() - 1));
                        mEditText.setSelection(s.toString().length() - 1);
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
