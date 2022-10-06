package com.ssafy.ng.api.service;

import com.ssafy.ng.config.IPFSConfig;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Service
public class IPFSServiceImpl implements IPFSService {

    @Autowired
    private IPFSConfig ipfsConfig;

    @Override
    public String saveFile(MultipartFile file) {
        try {
            InputStream is = new ByteArrayInputStream(file.getBytes());
            NamedStreamable.InputStreamWrapper inputStreamWrapper = new NamedStreamable.InputStreamWrapper(is);
            IPFS ipfs = ipfsConfig.ipfs;

            MerkleNode merkleNode = ipfs.add(inputStreamWrapper).get(0);
            System.out.println(merkleNode.hash.toBase58());

            return merkleNode.hash.toBase58();
        }catch (Exception e) {
            throw new RuntimeException("Error whilst communicationg with the IPFS node", e);
        }
    }

    @Override
    public byte[] loadFile(String hash) {
        try {
            IPFS ipfs = ipfsConfig.ipfs;
            Multihash filePointer = Multihash.fromBase58(hash);
            byte[] fileContents = ipfs.cat(filePointer);
            return fileContents;
        }catch(Exception e) {
            throw new RuntimeException("Error whilst communication wigh the IPFS node", e);
        }
    }
}
