package taiwan.no1.app.ssfm.features.bottomsheet

import com.devrapid.dialogbuilder.QuickDialogBindingFragment
import com.devrapid.kotlinknifer.recyclerview.WrapContentLinearLayoutManager
import com.devrapid.kotlinknifer.recyclerview.itemdecorator.VerticalItemDecorator
import com.trello.rxlifecycle2.components.RxActivity
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.FragmentDialogPlaylistBinding
import taiwan.no1.app.ssfm.features.chart.ChartDialogViewModel
import taiwan.no1.app.ssfm.misc.extension.recyclerview.DFPlaylistAdapter
import taiwan.no1.app.ssfm.misc.extension.recyclerview.DataInfo
import taiwan.no1.app.ssfm.misc.extension.recyclerview.refreshAndChangeList
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemCase
import taiwan.no1.app.ssfm.models.usecases.FetchPlaylistCase

/**
 * @author  jieyi
 * @since   1/13/18
 */
fun RxActivity.quickDialogBindingFragment(entity: Any,
                                          playlistRes: MutableList<BaseEntity>,
                                          playlistInfo: DataInfo,
                                          fetchPlaylistCase: FetchPlaylistCase,
                                          addPlaylistItemCase: AddPlaylistItemCase) =
    QuickDialogBindingFragment.Builder<FragmentDialogPlaylistBinding>(this) {
        viewCustom = R.layout.fragment_dialog_playlist
    }.build().apply {
        bind = { binding ->
            binding.vm = ChartDialogViewModel(playlistRes.isEmpty(), fetchPlaylistCase).apply {
                onAttach(this@quickDialogBindingFragment)
                fetchedPlaylistCallback = {
                    playlistRes.refreshAndChangeList(it.subList(1, it.size),
                                                     1,
                                                     binding.adapter as DFPlaylistAdapter,
                                                     playlistInfo)
                }
                binding.layoutManager = WrapContentLinearLayoutManager(activity)
                binding.decoration = VerticalItemDecorator(20)
                binding.adapter = DFPlaylistAdapter(this@quickDialogBindingFragment,
                                                    R.layout.item_playlist_type_2,
                                                    playlistRes) { holder, item, _ ->
                    if (null == holder.binding.avm)
                        holder.binding.avm = RecyclerViewDialogPlaylistViewModel(item,
                                                                                 entity as BaseEntity,
                                                                                 addPlaylistItemCase)
                    else
                        (holder.binding.avm as RecyclerViewDialogPlaylistViewModel).setPlaylistItem(item)
                }
            }
        }
    }.apply { show() }