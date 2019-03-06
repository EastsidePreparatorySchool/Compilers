/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vx86;

import vx86.Vx86;

/**
 *
 * @author gmein
 */
  public interface RuntimeSupport {
        int getRuntimeAddress(String routine);

        void invokeRoutine(Vx86 vm, int addr);
        
        boolean isRuntimeRoutine(int addr);
    }