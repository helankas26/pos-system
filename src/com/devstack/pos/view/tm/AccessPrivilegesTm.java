package com.devstack.pos.view.tm;

import com.jfoenix.controls.JFXCheckBox;
import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccessPrivilegesTm {
    private Long id;
    private String accessPoint;
    private List<JFXCheckBox> operation;
    private Button delete;
    private Button modify;
}
