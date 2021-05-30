package com.lyl.gateway.client.core.disruptor.subcriber;

import com.lyl.gateway.client.core.shutdown.LylClientShutdownHook;
import com.lyl.gateway.register.client.spi.LylClientRegisterRepository;
import com.lyl.gateway.register.common.dto.MetaDataRegisterDTO;
import com.lyl.gateway.register.common.subscriber.ExecutorTypeSubscriber;
import com.lyl.gateway.register.common.type.DataType;

import java.io.IOException;
import java.net.Socket;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName LylClientMetadataExecutorSubcriber
 * @Description
 * @Author Administrator
 * @Date 2021/5/29 20:31
 **/
public class LylClientMetadataExecutorSubcriber implements ExecutorTypeSubscriber<MetaDataRegisterDTO> {

    private final LylClientRegisterRepository lylClientRegisterRepository;

    public LylClientMetadataExecutorSubcriber(final LylClientRegisterRepository lylClientRegisterRepository){
        this.lylClientRegisterRepository = lylClientRegisterRepository;
    }

    @Override
    public DataType getType() {
        return DataType.META_DATA;
    }

    @Override
    public void executor(Collection<MetaDataRegisterDTO> metaDataRegisterDTOList) {
        for (MetaDataRegisterDTO metaDataRegisterDTO : metaDataRegisterDTOList){
            while (true){
                try (Socket socket = new Socket(metaDataRegisterDTO.getHost(), metaDataRegisterDTO.getPort())){
                    break;
                } catch (IOException e){
                    try {
                        TimeUnit.MICROSECONDS.sleep(100);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            LylClientShutdownHook.delayOtherHooks();
            lylClientRegisterRepository.persistInterface(metaDataRegisterDTO);
        }
    }

}
