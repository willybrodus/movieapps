package com.company.core.utility.paging

import androidx.paging.rxjava2.RxPagingSource
import com.bumptech.glide.load.HttpException
import com.company.core.data.model.PageableModel
import io.reactivex.Single
import java.io.IOException

abstract class BaseRxPagingSource<singeleDataType : Any>(val startingpage : Int = 1 ) : RxPagingSource<Int, singeleDataType>(){

    abstract override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, singeleDataType>>

    open fun toLoadResult(data: PageableModel<List<singeleDataType>>, params: LoadParams<Int>): LoadResult<Int, singeleDataType> {
        val position = params.key ?: startingpage
        return try{
            LoadResult.Page(
                data = data.results,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == data.total_pages) null else position + 1
            )
        } catch (e: IOException){
            LoadResult.Error(e)
        } catch (h: HttpException) {
            LoadResult.Error(h)
        } catch (i : IndexOutOfBoundsException){
            LoadResult.Error(i)
        }

    }

}

abstract class BaseRxPagingSourceRoom<singeleDataType : Any> : RxPagingSource<Int, singeleDataType>(){

    abstract override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, singeleDataType>>

    open fun toLoadResult(data: List<singeleDataType>, params: LoadParams<Int>): LoadResult<Int, singeleDataType> {
        return try{
            LoadResult.Page(
                data = data,
                prevKey = null,
                nextKey =null
            )
        } catch (e: IOException){
            LoadResult.Error(e)
        } catch (h: HttpException) {
            LoadResult.Error(h)
        } catch (i : IndexOutOfBoundsException){
            LoadResult.Error(i)
        }

    }

}