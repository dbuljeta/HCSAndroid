package com.example.daniel.hcs.interfaces;

/**
 * Created by Daniel on 2/14/2018.
 */

public interface RequestListener {
    void failed(String message);

    void finished(String message);
}
