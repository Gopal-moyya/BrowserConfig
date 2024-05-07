package com.board.config.boardconfiggui.data.outputmodels.ipconfig;

import org.apache.commons.collections4.CollectionUtils;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name = "Port")
@XmlAccessorType(XmlAccessType.FIELD)
public class IpConfigPort {

    @XmlAttribute(name = "Name")
    String name;

    @XmlElement(name = "signalParam")
    List<SignalParam> signalParams;

    public IpConfigPort() {
    }

    public IpConfigPort(String name) {
        this.name = name;
    }

    public String getName() { return name;}

    public List<SignalParam> getSignalParams() {
        return signalParams;
    }

    public void setSignalParams(List<SignalParam> signalParams) {
        this.signalParams = signalParams;
    }

    public void addSignalParam(SignalParam signalParam) {
        if (Objects.isNull(signalParam)) {
            return;
        }

        if (CollectionUtils.isEmpty(this.signalParams)) {
            this.signalParams = new ArrayList<>();
        }
        this.signalParams.add(signalParam);
    }

    @Override
    public String toString() {
        return "Port{" +
                "name='" + name + '\'' +
                ", signalParam=" + signalParams +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IpConfigPort port = (IpConfigPort) o;
        return Objects.equals(name, port.name) && Objects.equals(signalParams, port.signalParams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, signalParams);
    }
}
