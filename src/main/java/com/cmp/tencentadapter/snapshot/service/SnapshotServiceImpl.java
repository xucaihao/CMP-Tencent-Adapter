package com.cmp.tencentadapter.snapshot.service;

import com.cmp.tencentadapter.common.*;
import com.cmp.tencentadapter.image.service.ImageServiceImpl;
import com.cmp.tencentadapter.region.model.res.RegionInfo;
import com.cmp.tencentadapter.region.model.res.ResRegions;
import com.cmp.tencentadapter.region.service.RegionService;
import com.cmp.tencentadapter.snapshot.model.req.ReqCreSnapshot;
import com.cmp.tencentadapter.snapshot.model.res.QSnapshot;
import com.cmp.tencentadapter.snapshot.model.res.ResSnapshots;
import com.cmp.tencentadapter.snapshot.model.res.SnapshotInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.qcloud.Module.Snapshot;
import com.qcloud.Utilities.Json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static com.cmp.tencentadapter.common.Constance.GET;
import static com.cmp.tencentadapter.common.Constance.SUCCESS;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class SnapshotServiceImpl implements SnapshotService {

    private static final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

    @Autowired
    private RegionService regionService;

    private SnapshotInfo convertSnapshot(QSnapshot snapshot, String regionId) {
        SnapshotInfo resSnapshot = new SnapshotInfo();
        resSnapshot.setSnapshotId(snapshot.getSnapshotId());
        resSnapshot.setSnapshotName(snapshot.getSnapshotName());
        resSnapshot.setStatus(snapshot.getSnapshotStatus());
        resSnapshot.setPercent(snapshot.getPercent());
        if (!StringUtils.isEmpty(snapshot.getCreateTime())) {
            String createdTime = snapshot.getCreateTime()
                    .replace("T", " ")
                    .replace("Z", " ");
            resSnapshot.setCreationTime(createdTime);
        }
        resSnapshot.setEncrypted(snapshot.isEncrypt());
        resSnapshot.setSourceDiskId(snapshot.getStorageId());
        resSnapshot.setSourceDiskType(snapshot.getDiskType());
        resSnapshot.setSourceDiskSize(snapshot.getStorageSize());
        resSnapshot.setRegionId(regionId);
        return resSnapshot;
    }

    /**
     * 查询快照列表
     *
     * @param cloud 云（用户提供ak、sk）
     * @return 快照列表
     */
    @Override
    public ResSnapshots describeSnapshots(CloudEntity cloud) {
        if (TencentClient.getStatus()) {
            try {
                List<RegionInfo> regions = new ArrayList<>();
                RegionInfo regionInfo = new RegionInfo();
                regionInfo.setRegionId("cd");
                regions.add(regionInfo);
                ResRegions resRegions = new ResRegions(regions);
                //                ResRegions resRegions = regionService.describeRegions(cloud);
                List<CompletionStage<List<SnapshotInfo>>> futures = resRegions.getRegions().stream().map(region ->
                        CompletableFuture.supplyAsync(() -> {
                            try {
                                TreeMap<String, Object> config = TencentClient.initConfig(cloud, GET, region.getRegionId());
                                TreeMap<String, Object> param = new TreeMap<>();
                                param.put("Limit", 50);
                                String result = TencentClient.call(config, new Snapshot(), "DescribeSnapshots", param);
                                JSONObject jsonResult = new JSONObject(result);
                                if (!SUCCESS.equals(jsonResult.getString("codeDesc").toLowerCase())) {
                                    throw new RestException(jsonResult.getString("message"), BAD_REQUEST.value());
                                } else {
                                    String snapshotSet = jsonResult.getJSONArray("snapshotSet")
                                            .toString();
                                    return Optional.ofNullable(JsonUtil.stringToGenericObject(snapshotSet,
                                            new TypeReference<List<QSnapshot>>() {
                                            })).map(snapshots -> snapshots.stream().map(snapshot ->
                                                    convertSnapshot(snapshot, region.getRegionId())
                                            ).collect(toList())
                                    ).orElseThrow(() -> new RestException("", BAD_REQUEST.value()));
                                }
                            } catch (Exception e) {
                                logger.error("describeSnapshots in region: {} occurred error: {}", region.getRegionId(), e.getMessage());
                                return null;
                            }
                        })
                ).collect(toList());
                List<SnapshotInfo> snapshots = CommonUtil.aggregateList(CommonUtil.joinRes(futures));
                return new ResSnapshots(snapshots);
            } catch (Exception e) {
                throw (RuntimeException) e;
            }
        } else {
            List<SnapshotInfo> snapshots = TencentSimulator.getAll(SnapshotInfo.class);
            return new ResSnapshots(snapshots);
        }
    }

    /**
     * 创建快照
     *
     * @param cloud          云
     * @param reqCreSnapshot 请求体
     */
    @Override
    public void createSnapshot(CloudEntity cloud, ReqCreSnapshot reqCreSnapshot) {
        if (TencentClient.getStatus()) {
            try {
                TreeMap<String, Object> config = TencentClient.initConfig(cloud, GET, reqCreSnapshot.getRegionId());
                TreeMap<String, Object> param = new TreeMap<>();
                param.put("storageId", reqCreSnapshot.getDiskId());
                param.put("snapshotName", reqCreSnapshot.getSnapshotName());
                String result = TencentClient.call(config, new Snapshot(), "CreateSnapshot", param);
                JSONObject jsonResult = new JSONObject(result);
                if (!SUCCESS.equals(jsonResult.getString("codeDesc").toLowerCase())) {
                    throw new RestException(jsonResult.getString("message"), BAD_REQUEST.value());
                }
            } catch (Exception e) {
                logger.error("createSnapshot in region: {} occurred error: {}", reqCreSnapshot.getRegionId(), e.getMessage());
                throw (RuntimeException) e;
            }
        }
    }
}
