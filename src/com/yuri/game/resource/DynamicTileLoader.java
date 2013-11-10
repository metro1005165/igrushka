package com.yuri.game.resource;

import java.util.HashMap;

import com.yuri.game.resource.tiles.ResourceFileTileset;
import com.yuri.game.resource.tiles.TileCache;
import com.yuri.game.utils.Size;

import android.util.SparseArray;
import android.util.SparseIntArray;

public final class DynamicTileLoader {
	
	private final TileCache tileCache;
	
	private final SparseArray<ResourceFileTilesetLoadList> preparedTilesetsByResourceId = new SparseArray<ResourceFileTilesetLoadList>();
	private final HashMap<String, ResourceFileTilesetLoadList> preparedTilesetsByResourceName = new HashMap<String, ResourceFileTilesetLoadList>();
	private int currentTileStoreIndex;
	
	private static final class ResourceFileTilesetLoadList {
		public final ResourceFileTileset tileset;
		public final SparseIntArray tileIDsToLoadPerLocalID = new SparseIntArray();
		public ResourceFileTilesetLoadList(ResourceFileTileset tileset) {
			this.tileset = tileset;
		}
	}
	
	public DynamicTileLoader(TileCache tileCache) {
		this.tileCache = tileCache;
		initialize();
	}
	
	private void initialize() {
		preparedTilesetsByResourceId.clear();
		preparedTilesetsByResourceName.clear();
		currentTileStoreIndex = tileCache.getMaxTileID();
	}
	
	public void prepareTileset(int resourceId, String tilesetName, Size numTiles, Size destinationTileSize) {
		ResourceFileTileset b = new ResourceFileTileset(resourceId, tilesetName, numTiles, destinationTileSize);
		ResourceFileTilesetLoadList loadList = new ResourceFileTilesetLoadList(b);
		preparedTilesetsByResourceId.put(resourceId, loadList);
		preparedTilesetsByResourceName.put(tilesetName, loadList);
	}
	private ResourceFileTilesetLoadList getTilesetBitmap(int tilesetImageResourceID) {
		return preparedTilesetsByResourceId.get(tilesetImageResourceID);
	}
	private ResourceFileTilesetLoadList getTilesetBitmap(String tilesetName) {
		return preparedTilesetsByResourceName.get(tilesetName);
	}
	
	public int prepareTileID(int tilesetImageResourceID, int localID) {
		ResourceFileTilesetLoadList b = getTilesetBitmap(tilesetImageResourceID);
		return prepareTileID(b, localID);
	}

	public int prepareTileID(String tilesetName, int localID) {
		ResourceFileTilesetLoadList b = getTilesetBitmap(tilesetName);
		return prepareTileID(b, localID);
	}
	public Size getTilesetSize(String tilesetName) {
		ResourceFileTilesetLoadList b = getTilesetBitmap(tilesetName);
		return b.tileset.destinationTileSize;
	}
	
	private int prepareTileID(ResourceFileTilesetLoadList tileset, int localID) {
		int tileID = tileset.tileIDsToLoadPerLocalID.get(localID);
		if (tileID == 0) {
			++currentTileStoreIndex;
			tileID = currentTileStoreIndex;
			tileset.tileIDsToLoadPerLocalID.put(localID, tileID);
		}
		return tileID;
	}
	
	public void flush() {
		tileCache.allocateMaxTileID(currentTileStoreIndex);	
		for (int i = 0; i < preparedTilesetsByResourceId.size(); ++i) {
			ResourceFileTilesetLoadList e = preparedTilesetsByResourceId.valueAt(i);
			ResourceFileTileset tileset = e.tileset;
			SparseIntArray tileIDsToLoad = e.tileIDsToLoadPerLocalID;
			for (int j = 0; j < tileIDsToLoad.size(); ++j) {
				tileCache.setTile(tileIDsToLoad.valueAt(j), tileset, tileIDsToLoad.keyAt(j));
			}
		}
	}
}
